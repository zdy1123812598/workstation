package cn.net.wanji.kafkamanager.producer;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @ClassName kafkaHelper
 * @Description TODO
 * @Date 2021/3/18 14:46
 * @Version 1.0
 */
@Component
@Slf4j
public class CustomKafkaProducer {

    @Qualifier("customKafkaTemplate")
    @Autowired
    private KafkaTemplate customKafkaTemplate;

    private Logger logger = LoggerFactory.getLogger("Kafka");

    // 异步发送方法：
/*    public void sendMessage(String topic, String key, String value) {
        ListenableFuture<SendResult<String, String>> future = customKafkaTemplate.send(topic, key, value.getBytes());
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info(" with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message " + ex.getMessage());
            }
        });
    }*/

    //kafka异步发送
    public void sendMessage(String topic, String key, String value, Integer use) {
        if (use == 0) {
            logger.info("wanji kafka已停用");
            return;
        }
        long startTime = System.currentTimeMillis();
        try {
            if (value == null) {
                customKafkaTemplate.send(topic, key, null).addCallback(success -> {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime > 100) {
                        logger.info("[{}]数据发送成功,但耗时严重,数据包大小[{}],耗时:[{}]ms", topic, value.getBytes().length, endTime - startTime);
                    }
                }, failue -> {
                    long endTime = System.currentTimeMillis();
                    logger.info("[{}]数据发送失败,数据包大小[{}],耗时:[{}]ms,[{}]", topic, value.getBytes().length, endTime - startTime, failue.getMessage());

                });
            } else {
                customKafkaTemplate.send(topic, key, value.getBytes()).addCallback(success -> {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime > 100) {
                        logger.info("[{}]数据发送成功,但耗时严重,数据包大小[{}],耗时:[{}]ms", topic, value.getBytes().length, endTime - startTime);
                    }
                }, failue -> {
                    long endTime = System.currentTimeMillis();
                    logger.info("[{}]数据发送失败,数据包大小[{}],耗时:[{}]ms,[{}]", topic, value.getBytes().length, endTime - startTime, failue.getMessage());

                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //log.error("kafka发送超时",ex);

        }

        long endTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - startTime > 100) {
            logger.info("[{}]Kafka异步发送遇到阻塞,耗时严重,数据包大小[{}],耗时:[{}]ms", topic, value.getBytes().length, endTime - startTime);
        }

    }
}
