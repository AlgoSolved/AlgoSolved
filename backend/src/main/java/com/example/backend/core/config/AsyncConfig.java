package com.example.backend.core.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10); //적절한 값 설정 필요
    executor.setMaxPoolSize(100);
    executor.setQueueCapacity(50);
    executor.setThreadNamePrefix("GithubRepoAsync-");
    executor.initialize();
    return executor;
  }
}
