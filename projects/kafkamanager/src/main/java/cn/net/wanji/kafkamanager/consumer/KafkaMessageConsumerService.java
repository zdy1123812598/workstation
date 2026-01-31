package cn.net.wanji.kafkamanager.consumer;

import cn.net.wanji.kafkamanager.cache.TopicHistoryDownCache;
import cn.net.wanji.kafkamanager.cache.TopicHistoryUpCache;
import cn.net.wanji.kafkamanager.producer.CustomKafkaProducer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @Author zdy
 * @Date 2025/11/13
 * 逻辑实现
 */
@Slf4j
@Service
public class KafkaMessageConsumerService implements MessageListener<String, String>, ConsumerSeekAware {

    @Autowired
    private CustomKafkaProducer customKafkaProducer;

    private final DynamicKafkaConsumerConfig dynamicConfig;

    private static final String ORGCODE = "orgCode";
    private static final String ID = "id";

    /**
     * 重构
     */
    public KafkaMessageConsumerService(DynamicKafkaConsumerConfig dynamicConfig) {
        this.dynamicConfig = dynamicConfig;
    }


    /**
     * 设置
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        long startTime = dynamicConfig.getStartTime();
        if (startTime > 0) {
            for (Map.Entry<TopicPartition, Long> partitionEntry : assignments.entrySet()) {
                String topic = partitionEntry.getKey().topic();
                if (dynamicConfig.getTopics().contains(topic)) {
                    callback.seekToTimestamp(Collections.singleton(partitionEntry.getKey()), startTime);
                    log.debug("onPartitionsAssigned历史已定位分区 [%s] 到起始时间戳：%dms%n", partitionEntry, startTime);
                }
            }
        } else {
            long curtime = System.currentTimeMillis();
            for (Map.Entry<TopicPartition, Long> partitionEntry : assignments.entrySet()) {
                String topic = partitionEntry.getKey().topic();
                if (dynamicConfig.getTopics().contains(topic)) {
                    callback.seekToTimestamp(Collections.singleton(partitionEntry.getKey()), curtime);
                    log.debug("onPartitionsAssigned实时已定位分区 [%s] 到起始时间戳：%dms%n", partitionEntry, startTime);
                }
            }
        }
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

    }

    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
    }

    @Override
    public void onIdleContainer(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
    }

    /**
     * 业务消息
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            long messageTimestamp = record.timestamp();
            boolean isTimeValid = true;
            if (dynamicConfig.getStartTime() > 0 && messageTimestamp < dynamicConfig.getStartTime()) {
                isTimeValid = false;
            }
            if (dynamicConfig.getEndTime() > 0 && messageTimestamp > dynamicConfig.getEndTime()) {
                isTimeValid = false;
            }

            if (isTimeValid) {
                String topic = record.topic();
                String key = record.key();
                int partition = record.partition();
                long offset = record.offset();
                String value = record.value();
                String topicTarget = dynamicConfig.getTopicTarget();
                long timestamp = System.currentTimeMillis();
                if (dynamicConfig.getStartTime() < 0) {
                    customKafkaProducer.sendMessage(topicTarget, topicTarget + "-" + String.valueOf(timestamp), value, 1);
                } else {
                    Map map = JSON.parseObject(value);
                    String orgCode = map.get(ORGCODE).toString();
                    if (orgCode.equals("1")) {
                        TopicHistoryUpCache.put(map.get(ID).toString(), value);
                    } else {
                        TopicHistoryDownCache.put(map.get(ID).toString(), value);
                    }
                }
            } else {
                log.debug("onMessage过滤时间范围外的消息：Topic=%s, Time=%dms%n", record.topic(), messageTimestamp);
            }
        } catch (Exception e) {
            log.error("onMessage Exception", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 100)
    public void handleUpTask() {
        String topicTarget = dynamicConfig.getTopicTarget();
        if (!TopicHistoryUpCache.values().isEmpty()) {
            Optional<String> value = TopicHistoryUpCache.global_Map.values().stream().findFirst();
            if (value.isPresent()) {
                long timestamp = System.currentTimeMillis();
                Map map = JSON.parseObject(value.get().toString());
                customKafkaProducer.sendMessage(topicTarget, topicTarget + "-" + String.valueOf(timestamp), value.toString(),1);
                TopicHistoryUpCache.remove(map.get(ID).toString());
            }
        }
    }

    @Scheduled(fixedRate = 100)
    public void handleDownTaskT() {
        String topicTarget = dynamicConfig.getTopicTarget();
        if (!TopicHistoryDownCache.values().isEmpty()) {
            Optional<String> value = TopicHistoryDownCache.global_Map.values().stream().findFirst();
            if (value.isPresent()) {
                long timestamp = System.currentTimeMillis();
                Map map = JSON.parseObject(value.get().toString());
                customKafkaProducer.sendMessage(topicTarget, topicTarget + "-" + String.valueOf(timestamp), value.toString(),1);
                TopicHistoryDownCache.remove(map.get(ID).toString());
            }
        }
    }

}