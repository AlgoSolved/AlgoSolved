package com.example.backend.github;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.service.SyncWithGithubService;
import com.example.backend.lib.GithubClient;
import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.domain.ProgrammersProblem;
import com.example.backend.problem.service.ProblemService;
import com.example.backend.solution.common.enums.LanguageType;
import com.example.backend.solution.domain.Solution;
import com.example.backend.solution.repository.SolutionRepository;
import com.example.backend.solution.service.SolutionService;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;
import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SyncWithGithubServiceTest {

    User user;
    @Mock private GithubClient githubClient;
    @Mock private GithubRepositoryRepository githubRepositoryRepository;
    @Mock private UserRepository userRepository;
    @Mock private SolutionRepository solutionRepository;
    @Mock private ProblemService problemService;
    @Mock private SolutionService solutionService;

    @InjectMocks private SyncWithGithubService syncWithGithubService;

    @BeforeEach
    public void setUp() {
        user =
                User.builder()
                        .username("uchan")
                        .name("uchan")
                        .profileImageUrl("image-url")
                        .githubUrl("github-url")
                        .accessToken("accesstoken")
                        .build();
    }

    @Nested
    @DisplayName("sync 테스트")
    class SyncTest {

        @Test
        @DisplayName("유저가 없을 경우 false 를 반환한다.")
        public void userIsNotExistSyncTest() {
            String userName = "uchan";
            String repositoryName = "repo";
            when(userRepository.findByUsername(userName)).thenReturn(null);

            Assertions.assertFalse(syncWithGithubService.connect(userName, repositoryName));
        }

        @Test
        @DisplayName("해당 레포지토리가 존재하지 않을 경우 false 를 반환한다.")
        public void repositoryIsNotExistSyncTest() {
            String userName = "uchan";
            String repositoryName = "repo";
            when(userRepository.findByUsername(userName)).thenReturn(user);
            when(githubClient.isPathExist(repositoryName)).thenReturn(false);

            Assertions.assertFalse(syncWithGithubService.connect(userName, repositoryName));
        }

        @Test
        @DisplayName("해당 레포지토리가 이미 존재할 경우 false 를 반환한다.")
        public void repositoryIsExistSyncTest() {
            String userName = "uchan";
            String repositoryName = "repo";
            when(userRepository.findByUsername(userName)).thenReturn(user);
            when(githubClient.isPathExist(repositoryName)).thenReturn(true);
            when(githubRepositoryRepository.existsByRepo(repositoryName)).thenReturn(true);

            Assertions.assertFalse(syncWithGithubService.connect(userName, repositoryName));
        }

        @Test
        @DisplayName("githubRepository를 저장하고 true를 반환한다.")
        public void saveGithubRepositoryAndReturnTrueSyncTest() {
            String userName = "uchan";
            String repositoryName = "repo";
            when(userRepository.findByUsername(userName)).thenReturn(user);
            when(githubClient.isPathExist(repositoryName)).thenReturn(true);
            when(githubRepositoryRepository.existsByRepo(repositoryName)).thenReturn(false);

            Assertions.assertTrue(syncWithGithubService.connect(userName, repositoryName));
        }
    }

    @Nested
    @DisplayName("fetch 테스트")
    class FetchTest {

        @Test
        @DisplayName("해당 레포지토리에 백준 솔루션 파일을 가져오고, 소스코드를 solution에 저장하는 메소드들을 호출한다.")
        public void BackjoonSolutionFileTest() {
            user =
                    User.builder()
                            .username("uchan")
                            .name("uchan")
                            .profileImageUrl("image-url")
                            .githubUrl("github-url")
                            .accessToken("accesstoken")
                            .build();
            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo("repo").build();

            BaekjoonProblem problem = Instancio.create(BaekjoonProblem.class);
            Solution solution = Instancio.create(Solution.class);
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("백준/Bronze/1000. A＋B/A＋B.py"));
            when(githubClient.getContent(githubRepository.getRepo(), "백준/Bronze/1000. A＋B/A＋B.py"))
                    .thenReturn("a, b = map(int, input().split()); print(a+b)");
            when(problemService.getOrCreateFromFile("백준/Bronze/1000. A＋B/A＋B.py"))
                    .thenReturn(problem);


            boolean success = syncWithGithubService.fetch(githubRepository);

            Assertions.assertTrue(success);
            verify(githubClient).getAllFiles(githubRepository.getRepo());
            verify(githubClient).getContent(githubRepository.getRepo(), "백준/Bronze/1000. A＋B/A＋B.py");
            verify(solutionService).createSolution(githubRepository, problem, LanguageType.python, "a, b = map(int, input().split()); print(a+b)");
        }

        @Test
        @DisplayName("해당 레포지토리에 프로그래머스 솔루션 파일을 가져오고, 소스코드를 solution에 저장하는 메소드들을 호출한다.")
        public void ProgrammersSolutionFileTest() {
            User user =
                    User.builder()
                            .username("uchan")
                            .name("uchan")
                            .profileImageUrl("image-url")
                            .githubUrl("github-url")
                            .accessToken("accesstoken")
                            .build();
            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo("repo").build();
            ProgrammersProblem problem = Instancio.create(ProgrammersProblem.class);
            Solution solution = Instancio.create(Solution.class);
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"));
            when(githubClient.getContent(
                            githubRepository.getRepo(), "프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"))
                    .thenReturn("def solution(a, b): return a + b");
            when(problemService.getOrCreateFromFile("프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"))
                    .thenReturn(problem);


            boolean success = syncWithGithubService.fetch(githubRepository);

            Assertions.assertTrue(success);
            verify(githubClient).getAllFiles(githubRepository.getRepo());
            verify(githubClient).getContent(githubRepository.getRepo(), "프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py");
            verify(solutionService).createSolution(githubRepository, problem, LanguageType.python, "def solution(a, b): return a + b");
        }
    }
}
