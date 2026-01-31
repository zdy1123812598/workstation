package cn.net.wanji.kafkamanager.service.impl;


import cn.net.wanji.kafkamanager.consumer.DynamicKafkaConsumerConfig;
import cn.net.wanji.kafkamanager.consumer.DynamicKafkaConsumerFactory;
import cn.net.wanji.kafkamanager.consumer.KafkaMessageConsumerService;
import cn.net.wanji.kafkamanager.service.SimuKafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zdy
 * @Date 2025/11/13
 * 业务类
 */
@Slf4j
@Service
public class SimuKafkaConsumerServiceImpl implements SimuKafkaConsumerService {

    private final DynamicKafkaConsumerFactory consumerFactory;
    private final DynamicKafkaConsumerConfig dynamicConfig;
    private final KafkaMessageConsumerService messageConsumerService;

    public SimuKafkaConsumerServiceImpl(DynamicKafkaConsumerFactory consumerFactory, DynamicKafkaConsumerConfig dynamicConfig, KafkaMessageConsumerService messageConsumerService) {
        this.consumerFactory = consumerFactory;
        this.dynamicConfig = dynamicConfig;
        this.messageConsumerService = messageConsumerService;
    }

    /**
     * 启动
     *
     * @param topics    待消费 Topic 列表
     * @param startTime 起始时间戳（毫秒，可选，默认-1）
     * @param endTime   结束时间戳（毫秒，可选，默认-1）
     * @return true 成功  false 失败
     */
    @Override
    public Boolean startConsumer(List<String> topics,
                                 String topicTarget,
                                 long startTime,
                                 long endTime) {
        Boolean flag = false;
        try {
            dynamicConfig.setTopics(topics);
            dynamicConfig.setTopicTarget(topicTarget);
            dynamicConfig.setStartTime(startTime);
            dynamicConfig.setEndTime(endTime);
            consumerFactory.startConsumer(messageConsumerService, dynamicConfig);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 停止
     *
     * @return true 成功  false 失败
     */
    @Override
    public Boolean stopConsumer() {
        Boolean flag = false;
        try {
            consumerFactory.stopConsumer(dynamicConfig);
            dynamicConfig.setConsumerEnabled(false);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询
     *
     * @return true 运行  false 停止
     */
    @Override
    public Boolean getConsumerStatus() {
        boolean isRunning = consumerFactory.isConsumerRunning();
        log.info("consumerStatus: %s, currentTopics: %s, timeRange: %dms ~ %dms", isRunning ? "RUNNING" : "STOPPED",
                dynamicConfig.getTopics(),
                dynamicConfig.getStartTime(),
                dynamicConfig.getEndTime());
        return isRunning;
    }

}
