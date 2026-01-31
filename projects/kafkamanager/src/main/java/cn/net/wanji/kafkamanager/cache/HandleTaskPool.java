package cn.net.wanji.kafkamanager.cache;

import cn.net.wanji.kafkamanager.producer.CustomKafkaProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/*@Configuration
@EnableAsync*/
@Component
public class HandleTaskPool {

    @Resource
    private CustomKafkaProducer customKafkaProducer;


    /*@Bean("threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(200000);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }*/

    /*@Async("threadPoolTaskExecutor")*/
    //@Scheduled(cron = "*/1 * * * * ?")
    /*@Scheduled(fixedRate = 100)
    public void handleTask() {
        String topicTarget = "kafka-test";
        if (!TopicFirstCache.values().isEmpty()) {
            Collection<String> cacheList = TopicFirstCache.global_Map.values();
            Optional<String> value = TopicFirstCache.global_Map.values().stream().findFirst();
            if (value.isPresent()) {
                long timestamp = System.currentTimeMillis();
                Map map = JSON.parseObject(value.get().toString());
                map.put("topic", "1");
                map.put("offset", "");
                map.put("messageTimestamp", timestamp);
                customKafkaProducer.sendMessage(topicTarget, topicTarget + "-" + String.valueOf(timestamp), JSON.toJSONString(map));
                TopicFirstCache.remove(map.get("time").toString());
                *//*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*//*
            }
        }

    }*/

}
