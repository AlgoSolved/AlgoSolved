package com.example.backend.batch;

import com.example.backend.batch.dto.GithubSyncDto;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.lib.GithubClient;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GithubProblemSyncSteps {

    private final GithubClient githubClient;
    private final ProblemService problemService;

    @StepScope
    public ItemReader<GithubSyncDto> excelRequestReader(String repo) throws IOException {
        // TODO: 메모리 Out of Range 를 피하기 위해 ListItemReader 대신 페이지 네이션 가능한 방법 필요
        List<GithubSyncDto> githubSyncDtos = new ArrayList<GithubSyncDto>();
        List<String> solutionFiles =
                githubClient.getAllFiles(repo).stream()
                        .filter(file -> file.startsWith("백준") || file.startsWith("프로그래머스"))
                        .filter(LanguageType::containsExtension)
                        .toList();

        for (String file : solutionFiles) {
            String sourceCode = githubClient.getContent(repo, file);
            GithubSyncDto githubSyncDto =
                    GithubSyncDto.builder().file(file).sourceCode(sourceCode).build();
            githubSyncDtos.add(githubSyncDto);
        }

        return new ListItemReader<GithubSyncDto>(githubSyncDtos);
    }

    @StepScope
    public ItemProcessor<GithubSyncDto, Solution> excelRequestProcessor(
            SolutionRepository solutionRepository, GithubRepository githubRepository) {

        return new ItemProcessor<GithubSyncDto, Solution>() {
            @Override
            public Solution process(GithubSyncDto githubSyncDto) throws Exception {
                String file = githubSyncDto.getFile();
                String sourceCode = githubSyncDto.getSourceCode();
                Problem problem = problemService.getOrCreateFromFile(file);

                LanguageType languageType = LanguageType.getLanguageType(file);

                Solution solution =
                        Solution.builder()
                                .githubRepository(githubRepository)
                                .language(languageType)
                                .sourceCode(sourceCode)
                                .problem(problem)
                                .build();

                String hashKey = solution.generateHashKey();
                if (!solutionRepository.existsByHashKey(hashKey)) {
                    return null;
                }

                return solution;
            }
        };
    }

    @StepScope
    public ItemWriter<Solution> excelRequestWriter(SolutionRepository solutionRepository) {

        return new ItemWriter<Solution>() {
            @Override
            public void write(List<? extends Solution> items) throws Exception {
                solutionRepository.saveAll(items);
            }
        };
    }
}
