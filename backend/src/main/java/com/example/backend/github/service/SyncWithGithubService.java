package com.example.backend.github.service;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.lib.GithubClient;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncWithGithubService {

    private final GithubClient githubClient;
    private final GithubRepositoryRepository githubRepositoryRepository;
    private final UserRepository userRepository;
    private final JobLauncher asyncJobLauncher;
    private final JobRegistry jobRegistry;
    private final JobExplorer jobExplorer;

    public boolean connect(String userName, String repositoryName) {
        try {
            User user = userRepository.findByUsername(userName);
            if (user == null) {
                return false;
            }

            if (!githubClient.isPathExist(repositoryName)) {
                return false;
            }

            if (githubRepositoryRepository.existsByRepo(repositoryName)) {
                return false;
            }

            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo(repositoryName).build();
            githubRepositoryRepository.save(githubRepository);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Async
    public List<String[]> fetch(GithubRepository githubRepository) {
        try {
            List<String[]> result = new ArrayList<>();
            String repo = githubRepository.getRepo();

            List<String> solutionFiles =
                    githubClient.getAllFiles(repo).stream()
                            .filter(file -> file.startsWith("백준") || file.startsWith("프로그래머스"))
                            .filter(LanguageType::containsExtension)
                            .toList();

            for (String file : solutionFiles) {
                String sourceCode = githubClient.getContent(repo, file);
                if (sourceCode != null) {
                    result.add(new String[] {file, sourceCode});
                }
            }

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void fetchJob(GithubRepository githubRepository) {
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .addString("repo", githubRepository.getRepo())
                .addString("startTime", DateTime.now().toString())
                .toJobParameters();

        try {
            Job job = jobRegistry.getJob("githubProblemSyncJob");
            JobExecution jobExecution = asyncJobLauncher.run(job, jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | NoSuchJobException | JobRestartException e) {
            e.printStackTrace();
        }
    }

}
