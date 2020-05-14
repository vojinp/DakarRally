package com.htec.vojinpesalj.dakarrally.config;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ThreadInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(ThreadInfo.CORE_POOL_SIZE);
        executor.setMaxPoolSize(ThreadInfo.MAX_POOL_SIZE);
        executor.setThreadNamePrefix("default_task_executor_thread");
        executor.initialize();

        return executor;
    }
}
