package com.example.backend.batch;

import com.example.backend.batch.dto.GithubSyncDto;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class GithubBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BasicBatchConfigurer basicBatchConfigurer;
    private final GithubProblemSyncSteps githubProblemSyncSteps;
    private final SolutionRepository solutionRepository;
    private final GithubRepository githubRepository;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final JobRepository jobRepository;

    @Bean
    public JobLauncher asyncJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());

        return jobLauncher;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public Job githubProblemSyncJob() throws Exception {
        return jobBuilderFactory
                .get("githubProblemSyncJob")
                .incrementer(new RunIdIncrementer())
                .start(githubProblemSyncStep(null))
                .build();
    }

    @JobScope
    @Bean
    public Step githubProblemSyncStep(@Value("#{jobParameters[repo]}") String repo)
            throws Exception {

        return stepBuilderFactory
                .get("excelRequestExcute")
                .<GithubSyncDto, Solution>chunk(1000)
                .reader(githubProblemSyncSteps.excelRequestReader(repo))
                .processor(
                        githubProblemSyncSteps.excelRequestProcessor(
                                solutionRepository, githubRepository))
                .writer(githubProblemSyncSteps.excelRequestWriter(solutionRepository))
                .build();
    }
}
