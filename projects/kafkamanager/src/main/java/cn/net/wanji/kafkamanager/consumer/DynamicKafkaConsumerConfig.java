package cn.net.wanji.kafkamanager.consumer;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author zdy
 * @Date 2025/11/13
 * 动态消费配置
 */
@Component
@Data
public class DynamicKafkaConsumerConfig {
    /**
     * 初始化参数
     */
    private List<String> topics = new CopyOnWriteArrayList<>();
    private long startTime = -1;
    private long endTime = -1;
    private String topicTarget = "test";
    private volatile boolean consumerEnabled = false;
}
