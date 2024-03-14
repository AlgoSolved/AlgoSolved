package com.example.backend.github;

import static org.mockito.Mockito.when;

import com.example.backend.github.domain.GithubRepository;
import com.example.backend.github.repository.GithubRepositoryRepository;
import com.example.backend.github.service.SyncWithGithubService;
import com.example.backend.lib.GithubClient;
import com.example.backend.user.domain.User;
import com.example.backend.user.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SyncWithGithubServiceTest {

    User user;
    @Mock private GithubClient githubClient;
    @Mock private GithubRepositoryRepository githubRepositoryRepository;
    @Mock private UserRepository userRepository;
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
        @DisplayName("해당 레포지토리에 솔루션 파일이 없는 경우 빈 리스트를 반환한다.")
        public void solutionFilesIsNotExistFetchTest() {
            GithubRepository githubRepository = GithubRepository.builder().repo("repo").build();
            when(githubClient.getAllFiles(githubRepository.getRepo())).thenReturn(List.of());

            Assertions.assertEquals(syncWithGithubService.fetch(githubRepository), List.of());
        }

        @Test
        @DisplayName("해당 레포지토리에 백준 솔루션 파일이 있는 경우 파일과 코드를 반환한다.")
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
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("백준/Bronze/1000. A＋B/A＋B.py"));
            when(githubClient.getContent(githubRepository.getRepo(), "백준/Bronze/1000. A＋B/A＋B.py"))
                    .thenReturn("a, b = map(int, input().split()); print(a+b)");

            List<String[]> result = syncWithGithubService.fetch(githubRepository);
            Assertions.assertEquals(result.get(0)[0], "백준/Bronze/1000. A＋B/A＋B.py");
            Assertions.assertEquals(
                    result.get(0)[1], "a, b = map(int, input().split()); print(a+b)");
        }

        @Test
        @DisplayName("해당 레포지토리에 백준 솔루션 파일이 최대 길이 제한 보다 큰 경우 파일과 null을 반환한다.")
        public void BackjoonSolutionSizeOverFileTest() {
            user =
                    User.builder()
                            .username("uchan")
                            .name("uchan")
                            .profileImageUrl("image-url")
                            .githubUrl("github-url")
                            .build();
            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo("repo").build();
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("백준/Bronze/1000. A＋B/A＋B.py"));
            when(githubClient.getContent(githubRepository.getRepo(), "백준/Bronze/1000. A＋B/A＋B.py"))
                    .thenReturn(null);

            List<String[]> result = syncWithGithubService.fetch(githubRepository);
            Assertions.assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("해당 레포지토리에 프로그래머스 솔루션 파일이 있는 경우 파일과 코드를 반환한다.")
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
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"));
            when(githubClient.getContent(
                            githubRepository.getRepo(), "프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"))
                    .thenReturn("def solution(a, b): return a + b");

            List<String[]> result = syncWithGithubService.fetch(githubRepository);
            Assertions.assertEquals(result.get(0)[0], "프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py");
            Assertions.assertEquals(result.get(0)[1], "def solution(a, b): return a + b");
        }

        @Test
        @DisplayName("해당 레포지토리에 프로그래머스 솔루션 파일이 최대 길이 제한 보다 큰 경우 파일과 null을 반환한다.")
        public void ProgrammersSolutionSizeOverFileTest() {
            User user =
                    User.builder()
                            .username("uchan")
                            .name("uchan")
                            .profileImageUrl("image-url")
                            .githubUrl("github-url")
                            .build();
            GithubRepository githubRepository =
                    GithubRepository.builder().user(user).repo("repo").build();
            when(githubClient.getAllFiles(githubRepository.getRepo()))
                    .thenReturn(List.of("프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"));
            when(githubClient.getContent(
                            githubRepository.getRepo(), "프로그래머스/0/1. 두 정수 사이의 합/두 정수 사이의 합.py"))
                    .thenReturn(null);

            List<String[]> result = syncWithGithubService.fetch(githubRepository);
            Assertions.assertTrue(result.isEmpty());
        }
    }
}
