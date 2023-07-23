package com.hb.file.dispatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * @author zhaochengshui
 */
@Configuration
@Slf4j
public class ExecutorConfig {
    @Value("${media.upload.task.max-pool-size:10}")
    private int maxUploadSize;
    @Value("${media.upload.task.queue-capacity:1000}")
    private int queueCapacity;

    @Bean
    public ThreadPoolTaskExecutor fileTaskExecutor() {
        log.info("start fileTaskExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(maxUploadSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxUploadSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("fileTaskExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        executor.setRejectedExecutionHandler((r, executor1) -> log.debug("任务已满,需控制消费速度"));
        //执行初始化
        executor.initialize();

        return executor;
    }


}
