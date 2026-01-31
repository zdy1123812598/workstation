package cn.net.wanji.kafkamanager.controller;

import cn.net.wanji.kafkamanager.consumer.DynamicKafkaConsumerConfig;
import cn.net.wanji.kafkamanager.consumer.DynamicKafkaConsumerFactory;
import cn.net.wanji.kafkamanager.consumer.KafkaMessageConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kafka/consumer")
public class KafkaControl {

    private final DynamicKafkaConsumerFactory consumerFactory;
    private final DynamicKafkaConsumerConfig dynamicConfig;
    private final KafkaMessageConsumerService messageConsumerService;

    public KafkaControl(DynamicKafkaConsumerFactory consumerFactory,
                        DynamicKafkaConsumerConfig dynamicConfig,
                        KafkaMessageConsumerService messageConsumerService) {
        this.consumerFactory = consumerFactory;
        this.dynamicConfig = dynamicConfig;
        this.messageConsumerService = messageConsumerService;
    }

    /**
     * 启动消费者（支持动态传入 Topic 和时间范围）
     *
     * @param topics    待消费 Topic 列表
     * @param startTime 起始时间戳（毫秒，可选，默认-1）
     * @param endTime   结束时间戳（毫秒，可选，默认-1）
     */
    @PostMapping("/start")
    public String startConsumer(@RequestParam List<String> topics,
                                @RequestParam String topicTarget,
                                @RequestParam(required = false, defaultValue = "-1") long startTime,
                                @RequestParam(required = false, defaultValue = "-1") long endTime) {
        try {
            // 更新动态配置
            dynamicConfig.setTopics(topics);
            dynamicConfig.setTopicTarget(topicTarget);
            dynamicConfig.setStartTime(startTime);
            dynamicConfig.setEndTime(endTime);

            // 启动消费者（传入消息处理逻辑）
            consumerFactory.startConsumer(messageConsumerService, dynamicConfig);
            return "success: 消费者启动成功！";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    /**
     * 停止消费者
     */
    @PostMapping("/stop")
    public String stopConsumer() {
        consumerFactory.stopConsumer(dynamicConfig);
        dynamicConfig.setConsumerEnabled(false);
        return "success: 消费者已停止！";
    }

    /**
     * 查询消费者状态
     */
    @GetMapping("/status")
    public String getConsumerStatus() {
        boolean isRunning = consumerFactory.isConsumerRunning();
        return String.format("consumerStatus: %s, currentTopics: %s, timeRange: %dms ~ %dms",
                isRunning ? "RUNNING" : "STOPPED",
                dynamicConfig.getTopics(),
                dynamicConfig.getStartTime(),
                dynamicConfig.getEndTime());
    }

    /**
     * 动态更新消费 Topic（需先停止消费者，再重启）
     */
    @PutMapping("/update/topics")
    public String updateTopics(@RequestParam List<String> topics) {
        if (consumerFactory.isConsumerRunning()) {
            return "error: 请先停止消费者再更新 Topic！";
        }
        dynamicConfig.setTopics(topics);
        return "success: Topic 更新成功！新 Topic：" + topics;
    }

    /**
     * 动态更新时间范围（支持运行中更新）
     */
    @PutMapping("/update/time-range")
    public String updateTimeRange(@RequestParam(required = false, defaultValue = "-1") long startTime,
                                  @RequestParam(required = false, defaultValue = "-1") long endTime) {
        dynamicConfig.setStartTime(startTime);
        dynamicConfig.setEndTime(endTime);
        return "success: 时间范围更新成功！新范围：" + startTime + "ms ~ " + endTime + "ms";
    }

}