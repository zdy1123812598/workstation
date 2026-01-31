package cn.net.wanji.kafkamanager.cache;

import cn.net.wanji.kafkamanager.producer.CustomKafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*@Configuration
@EnableAsync*/
@Component
public class HandleTaskPoolT {
    @Resource
    private CustomKafkaProducer customKafkaProducer;


    /*@Bean("threadPoolTaskExecutorT")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(200000);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }*/

    /*@Async("threadPoolTaskExecutorT")*/
    //@Scheduled(cron = "*/1 * * * * ?")
    /*@Scheduled(fixedRate = 100)
    public void handleTaskT() {
        String topicTarget = "kafka-test";
        if (!TopicSecondCache.values().isEmpty()) {
            Collection<String> cacheList = TopicSecondCache.global_Map.values();
            Optional<String> value = TopicSecondCache.global_Map.values().stream().findFirst();
            if (value.isPresent()) {
                long timestamp = System.currentTimeMillis();
                Map map = JSON.parseObject(value.get().toString());
                map.put("topic", "3");
                map.put("offset", "");
                map.put("messageTimestamp", timestamp);
                customKafkaProducer.sendMessage(topicTarget, topicTarget + "-" + String.valueOf(timestamp), JSON.toJSONString(map));
                TopicSecondCache.remove(map.get("time").toString());
                *//*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*//*
            }
        }
    }*/

}
