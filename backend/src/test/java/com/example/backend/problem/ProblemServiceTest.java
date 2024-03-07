package com.example.backend.problem;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.backend.problem.domain.BaekjoonProblem;
import com.example.backend.problem.domain.Problem;
import com.example.backend.problem.domain.ProblemFactory;
import com.example.backend.problem.domain.ProgrammersProblem;
import com.example.backend.problem.repository.BaekjoonProblemRepository;
import com.example.backend.problem.repository.ProgrammersProblemRepository;
import com.example.backend.problem.service.ProblemService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceTest {
    @Mock private BaekjoonProblemRepository baekjoonProblemRepository;
    @Mock private ProgrammersProblemRepository programmersProblemRepository;

    @Mock private ProblemFactory problemFactory;

    @InjectMocks private ProblemService problemService;

    @Nested
    @DisplayName("getOrCreateFromFile 테스트")
    class getOrCreateFromFileTest {

        private Problem problem;

        @Test
        @DisplayName("백준 솔루션 파일에 해당하는 문제가 없는 경우 새로운 문제를 생성한다.")
        public void baekjoonProblemIsNotExistTest() {
            String file = "백준/Bronze/1000. A＋B/A＋B.py";
            problem =
                    BaekjoonProblem.builder()
                            .title("A+B")
                            .tier("Bronze")
                            .problemNumber(1000L)
                            .build();

            when(problemFactory.getFromFile(file)).thenReturn(problem);
            when(baekjoonProblemRepository.findByProblemNumber(1000L)).thenReturn(null);

            Problem result = problemService.getOrCreateFromFile(file);
            Assertions.assertEquals(problem, result);
            verify(baekjoonProblemRepository, times(1)).save((BaekjoonProblem) problem);
        }

        @Test
        @DisplayName("백준 솔루션 파일에 해당하는 문제가 있는 경우 해당 문제를 리턴한다.")
        public void baekjoonProblemIsExistTest() {
            String file = "백준/Bronze/1000. A＋B/A＋B.py";
            problem =
                    BaekjoonProblem.builder()
                            .title("A+B")
                            .tier("Bronze")
                            .problemNumber(1000L)
                            .build();

            when(problemFactory.getFromFile(file)).thenReturn(problem);
            when(baekjoonProblemRepository.findByProblemNumber(1000L))
                    .thenReturn((BaekjoonProblem) problem);

            Problem result = problemService.getOrCreateFromFile(file);
            Assertions.assertEquals(problem, result);
            verify(baekjoonProblemRepository, times(0)).save((BaekjoonProblem) problem);
        }

        @Test
        @DisplayName("프로그래머스 솔루션 파일에 해당하는 문제가 없는 경우 새로운 문제를 생성한다.")
        public void programmersProblemIsNotExistTest() {
            String file = "프로그래머스/1/12903. 가운데 글자 가져오기/가운데 글자 가져오기.py";
            problem =
                    ProgrammersProblem.builder()
                            .title("가운데 글자 가져오기")
                            .level(1)
                            .lessonNumber(12903L)
                            .build();

            when(problemFactory.getFromFile(file)).thenReturn(problem);
            when(programmersProblemRepository.findByLessonNumber(12903L)).thenReturn(null);

            Problem result = problemService.getOrCreateFromFile(file);
            Assertions.assertEquals(problem, result);
            verify(programmersProblemRepository, times(1)).save((ProgrammersProblem) problem);
        }

        @Test
        @DisplayName("프로그래머스 솔루션 파일에 해당하는 문제가 있는 경우 해당 문제를 리턴한다.")
        public void programmersProblemIsExistTest() {
            String file = "프로그래머스/1/12903. 가운데 글자 가져오기/가운데 글자 가져오기.py";
            problem =
                    ProgrammersProblem.builder()
                            .title("가운데 글자 가져오기")
                            .level(1)
                            .lessonNumber(12903L)
                            .build();

            when(problemFactory.getFromFile(file)).thenReturn(problem);
            when(programmersProblemRepository.findByLessonNumber(12903L))
                    .thenReturn((ProgrammersProblem) problem);

            Problem result = problemService.getOrCreateFromFile(file);
            Assertions.assertEquals(problem, result);
            verify(programmersProblemRepository, times(0)).save((ProgrammersProblem) problem);
        }
    }
}
