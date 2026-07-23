package com.thread.pool.dynamic.param.config;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class ThreadPoolConfig {

    private static ThreadPoolTaskExecutor sharedThreadPoolTaskExecutor;

    /**
     * 创建共享线程池（所有DomainService共用）
     */
    @Bean
    public ThreadPoolTaskExecutor sharedThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        int corePoolSize = Math.max(4, Runtime.getRuntime().availableProcessors() * 2);

        // 核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 最大线程数
        executor.setMaxPoolSize(corePoolSize * 2);
        // 队列容量
        executor.setQueueCapacity(1000);
        // 线程空闲时间
        executor.setKeepAliveSeconds(60);
        // 线程名前缀
        executor.setThreadNamePrefix("query-");
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        sharedThreadPoolTaskExecutor = executor;
        log.info("ThreadPoolConfig创建共享线程池成功，核心线程数: {}, 最大线程数: {}", corePoolSize, corePoolSize * 2);
        return executor;
    }

    /**
     * 应用关闭时自动关闭线程池
     */
    @PreDestroy
    public void shutdownThreadPoolTaskExecutor() {
        if (sharedThreadPoolTaskExecutor != null && !sharedThreadPoolTaskExecutor.getThreadPoolExecutor().isShutdown()) {
            log.info("ThreadPoolConfig开始关闭共享线程池...");
            sharedThreadPoolTaskExecutor.shutdown();
            try {
                if (!sharedThreadPoolTaskExecutor.getThreadPoolExecutor().awaitTermination(60, TimeUnit.SECONDS)) {
                    sharedThreadPoolTaskExecutor.getThreadPoolExecutor().shutdownNow();
                }
                log.info("ThreadPoolConfig共享线程池已关闭");
            } catch (InterruptedException e) {
                sharedThreadPoolTaskExecutor.getThreadPoolExecutor().shutdownNow();
                Thread.currentThread().interrupt();
                log.error("ThreadPoolConfig线程池关闭被中断", e);
            }
        }
    }

    /**
     * 提供静态方法获取线程池
     */
    public static ThreadPoolTaskExecutor getSharedThreadPoolTaskExecutor() {
        return sharedThreadPoolTaskExecutor;
    }

}
