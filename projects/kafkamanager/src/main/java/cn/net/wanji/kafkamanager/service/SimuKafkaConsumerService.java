package cn.net.wanji.kafkamanager.service;

import java.util.List;

public interface SimuKafkaConsumerService {

    Boolean startConsumer(List<String> topics,
                          String topicTarget,
                          long startTime,
                          long endTime);

    Boolean stopConsumer();

    Boolean getConsumerStatus();

}