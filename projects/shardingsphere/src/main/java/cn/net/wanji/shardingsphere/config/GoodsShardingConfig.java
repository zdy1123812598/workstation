package cn.net.wanji.sp.config;

import cn.hutool.core.date.DateUtil;
import cn.net.wanji.sp.entity.DynamicMonthTableEntity;
import cn.net.wanji.sp.utils.ShardingUtils;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GoodsShardingConfig implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {

    @Autowired
    private SplitTableByMonthConfig tableConfig;


    @Override
    //精确匹配逻辑
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
        Date date = preciseShardingValue.getValue();
        //获取分片键的日期格式为"2020_12"
        String tableSuffix = ShardingUtils.getSuffixByYearMonth(date);
        //匹配表
        for (String tableName : availableTargetNames) {
            return tableName + "_" + tableSuffix;
            /*if (tableName.endsWith(tableSuffix)) {
                return tableName;
            }*/
        }
        throw new IllegalArgumentException("未找到匹配的数据表");
    }

    @Override
    //范围匹配逻辑
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        List<String> list = new ArrayList<>();

        Range<Date> valueRange = rangeShardingValue.getValueRange();

        Date lowerDate = null;
        Date upperDate;
        //获取上限,下限
        if (null != valueRange.lowerEndpoint()) {
            lowerDate = valueRange.lowerEndpoint();
        } else {
            HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
            //起始时间点
            for (String tableName : availableTargetNames) {
                DynamicMonthTableEntity dynamicMonthTableEntity = tables.get(tableName);
                int yearTime = dynamicMonthTableEntity.getStartYear();
                int monthTime = dynamicMonthTableEntity.getStartMonth();
                upperDate = DateUtil.parse(yearTime + "-" + monthTime + "-01");
                if (null != upperDate) {
                    break;
                }
            }
        }

        if (null != valueRange.upperEndpoint()) {
            upperDate = valueRange.upperEndpoint();
        } else {
            upperDate = new Date();
        }
        String lowerSuffix = ShardingUtils.getSuffixByYearMonth(lowerDate);
        String upperSuffix = ShardingUtils.getSuffixByYearMonth(upperDate);
        TreeSet<String> suffixList = ShardingUtils.getSuffixListForRange(lowerSuffix, upperSuffix);
        for (String tableName : availableTargetNames) {
            for (String suffix : suffixList) {
                list.add(tableName + "_" + suffix);
            }
        }
        return list;
    }

    private boolean containTableName(Set<String> suffixList, String tableName) {
        boolean flag = false;
        for (String s : suffixList) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
