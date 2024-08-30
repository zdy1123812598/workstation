package com.zdy.threadpoolweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class ThreadPoolWebApplication {

    private final static Logger logger =
            LoggerFactory.getLogger(ThreadPoolWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ThreadPoolWebApplication.class, args);
    }

    @PreDestroy
    public void destory() {
        logger.info("服务结束，开始释放空间...");
        // 关闭线程池
    }

}
