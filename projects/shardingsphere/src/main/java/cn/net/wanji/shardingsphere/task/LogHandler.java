package cn.net.wanji.sp.task;

import cn.net.wanji.sp.config.SplitTableByMonthConfig;
import cn.net.wanji.sp.entity.DynamicMonthTableEntity;
import cn.net.wanji.sp.server.GoodsServer;
import cn.net.wanji.sp.utils.DateUtils;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.shardingsphere.underlying.common.config.exception.ShardingSphereConfigurationException;
import org.apache.shardingsphere.underlying.common.rule.DataNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class LogHandler {

    @Autowired
    private SplitTableByMonthConfig tableConfig;

    @Resource
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodsServer goodsServer;

    private static final Logger logger = LoggerFactory.getLogger(LogHandler.class);

    /**
     * 定时任务(每月最后一天中午12点生成表并刷新配置)
     */

    @Scheduled(cron = "0 0/1 * * * *")
    public void createTableJobHandler() throws Exception {
        //获取当前年月
        Integer year = Integer.parseInt(DateUtils.getYear());
        Integer month = Integer.parseInt(DateUtils.getMonth());
        Integer newYear = year;
        Integer newxtMonth = month;
        //12月开始任务时 生成次年一月份的表
        if (month > 11) {
            newYear += 1;
            newxtMonth = 1;
        } else {
            //生成次月表
            newxtMonth += 1;
        }
        // 所有以月份分片的表
        HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
        for (String name : tables.keySet()) {
            String newTable = name + "_" + year + "_" + month;
            String newNextTable = name + "_" + newYear + "_" + newxtMonth;
            // 这里判断表是否存在,创建表
            if (goodsServer.existTable(newTable) < 1) {
                goodsServer.createTable(tables.get(name).getTemplateTable(), newTable);
            }
            if (goodsServer.existTable(newNextTable) < 1) {
                goodsServer.createTable(tables.get(name).getTemplateTable(), newNextTable);
            }
        }
        //创建成功之后 刷新 actual-data-nodes
        actualTablesRefresh(year, month);
    }

    /**
     * 初始化起始时间到当前时间的表配置
     */
    @PostConstruct
    private void intData() {
        String year = DateUtils.getYear();
        String month = DateUtils.getMonth();
        actualTablesRefresh(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 动态更新表配置
     *
     * @param
     */

    public void actualTablesRefresh(Integer year, Integer month) {
        try {
            //获取shadingJdbc配置
            ShardingDataSource dataSource = (ShardingDataSource) this.dataSource;
            HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
            final Set<String> names = tables.keySet();
            //判断是否需要动态刷新配置
            if (names == null || names.size() == 0) {
                logger.error("没有动态分表配置");
                return;
            }
            //为每张动态表刷新配置
            for (String name : names) {
                TableRule tableRule = null;
                try {
                    //获取配置规则
                    tableRule = dataSource.getConnection().getRuntimeContext().getRule().getTableRule(name);
                } catch (ShardingSphereConfigurationException e) {
                    logger.error("报错啦" + e.getMessage());

                }
                //配置sharding表对应的实际表节点
                List<DataNode> dataNodes = tableRule.getActualDataNodes();
                Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
                actualDataNodesField.setAccessible(true);
                //新的表节点集合
                List<DataNode> newDataNodes = new ArrayList<>();
                //起始时间点
                DynamicMonthTableEntity dynamicMonthTableEntity = tables.get(name);
                int yearTime = dynamicMonthTableEntity.getStartYear();
                int monthTime = dynamicMonthTableEntity.getStartMonth();
                //获取sharding表名
                String dataSourceName = dataNodes.get(0).getDataSourceName();
                //往表节点中更新数据
                while (true) {
                    int tempTime = yearTime;
                    //起始年小于当前年
                    if (yearTime < year) {
                        while (yearTime <= year) {
                            //起始年份的年份配置节点生成
                            if (tempTime == yearTime) {
                                for (int j = monthTime; j < 13; j++) {
                                    DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                                    newDataNodes.add(dataNode);
                                }
                                yearTime++;
                                //中间年份的年份配置节点生成
                            } else if (yearTime > tempTime && yearTime != year) {
                                for (int j = 1; j < 13; j++) {
                                    DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + year + "_" + j);
                                    newDataNodes.add(dataNode);
                                }
                                yearTime++;
                            } else {
                                break;
                            }
                        }
                        //当前年份的年份配置节点生成
                        if (yearTime == year) {
                            for (int j = 1; j < month + 2; j++) {
                                DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                                newDataNodes.add(dataNode);
                            }
                            yearTime++;
                            break;
                        }
                    }

                    //起始年等于当前年
                    if (yearTime == year) {
                        for (int j = monthTime; j < month + 2; j++) {
                            DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                            newDataNodes.add(dataNode);
                        }
                        yearTime++;
                    }

                    if (yearTime > year) {
                        break;
                    }
                }
                actualDataNodesField.set(tableRule, newDataNodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("初始化 动态表单失败" + e.getMessage());
        }
    }

}

