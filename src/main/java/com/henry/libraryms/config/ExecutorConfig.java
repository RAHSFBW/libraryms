package com.henry.libraryms.config;

import com.henry.libraryms.common.constants.ThreadPoolConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class ExecutorConfig {

    @Bean("MyThreadExecutor")
    public Executor MyThreadExecutor() {
        log.info("start async executor");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        配置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(ThreadPoolConstant.CORE_POOL_SIZE);
//        配置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(ThreadPoolConstant.MAX_POOL_SIZE);
//        配置队列大小
        threadPoolTaskExecutor.setQueueCapacity(ThreadPoolConstant.QUEUE_CAPACITY);
//        配置线程池中线程的名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix(ThreadPoolConstant.THREAD_NAME_PREFIX);
//   HelloWorldServiceImpl     rejection-policy: 当pool已经达到max size时，如何处理新任务：
//        CallerRunsPolicy: 不在新线程中执行任务，而是由调用者所在的线程来执行；
//        AbortPolicy: 拒绝执行新任务，并抛出RejectedExecutionException异常；
//        DiscardPolicy：丢弃当前将要加入队列的任务；
//        DiscardOldestPolicy：丢弃任务队列中最旧的任务；
        threadPoolTaskExecutor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}

