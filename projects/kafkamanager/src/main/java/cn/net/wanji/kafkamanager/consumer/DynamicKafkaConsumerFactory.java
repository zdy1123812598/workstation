package cn.net.wanji.kafkamanager.consumer;

import cn.net.wanji.kafkamanager.cache.TopicHistoryDownCache;
import cn.net.wanji.kafkamanager.cache.TopicHistoryUpCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zdy
 * @Date 2025/11/13
 * 消费工厂
 */
@Slf4j
@Component
public class DynamicKafkaConsumerFactory implements DisposableBean {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.value-deserializer}")
    public String valueDeserializer;

    @Value("${spring.kafka.consumer.key-deserializer}")
    public String keyDeserializer;

    /**
     * 消费者容器
     */
    private ConcurrentMessageListenerContainer<String, String> consumerContainer;

    /**
     * 配置
     */
    private Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        return config;
    }

    /**
     * 启动
     *
     * @param messageListener
     * @param config
     */
    public synchronized void startConsumer(MessageListener<String, String> messageListener, DynamicKafkaConsumerConfig config) {
        stopConsumer(config);
        if (config.getTopics().isEmpty()) {
            throw new IllegalArgumentException("消费topic不能为空");
        }

        DefaultKafkaConsumerFactory<Object, Object> consumerFactory = new DefaultKafkaConsumerFactory<>(getConsumerConfig());
        ContainerProperties containerProperties = new ContainerProperties(config.getTopics().toArray(new String[0]));
        containerProperties.setMessageListener(messageListener);

        consumerContainer = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProperties);
        consumerContainer.setConcurrency(1);

        consumerContainer.start();
        config.setConsumerEnabled(true);
        log.debug("Kafka消费者启动成功 Topic：%s%n→ 时间范围：%dms ~ %dms%n", config.getTopics(), config.getStartTime(), config.getEndTime());
    }

    /**
     * 停止
     */
    public synchronized void stopConsumer(DynamicKafkaConsumerConfig config) {
        if (consumerContainer != null && consumerContainer.isRunning()) {
            try {
                this.destroy();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            TopicHistoryUpCache.removeAll();
            TopicHistoryDownCache.removeAll();
            clearTopicData(config.getTopicTarget());
            log.debug("Kafka消费者停止成功");
        }
    }

    /**
     * 状态
     *
     * @return
     */
    public boolean isConsumerRunning() {
        return consumerContainer != null && consumerContainer.isRunning();
    }

    /**
     * 清理数据
     *
     * @param topicName
     * @return
     */
    public synchronized Boolean clearTopicData(String topicName) {
        Boolean flag = false;
        AdminClient adminClient = AdminClient.create(getConsumerConfig());
        try {
            DeleteTopicsResult deleteResult = adminClient.deleteTopics(Collections.singleton(topicName));
            deleteResult.all().get();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adminClient.close();
        }
        return flag;
    }

    @Override
    public void destroy() throws Exception {
        if (null != consumerContainer) {
            consumerContainer.stop();
            consumerContainer = null;
        }
    }
}
