package org.algosolved.backend.user.service;

import lombok.RequiredArgsConstructor;

import org.algosolved.backend.common.enums.ExceptionStatus;
import org.algosolved.backend.common.exceptions.NotFoundException;
import org.algosolved.backend.github.domain.GithubRepository;
import org.algosolved.backend.lib.GithubClient;
import org.algosolved.backend.user.domain.User;
import org.algosolved.backend.user.dto.UserDTO.Profile;
import org.algosolved.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GithubClient githubClient;

    public Profile getUserProfile(Long id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        return Profile.mapToDTO(user, getSolutionCount(user.getGithubRepository()));
    }

    public Long getSolutionCount(GithubRepository githubRepository) {
        if (githubRepository == null) {
            return 0L;
        }
        return githubRepository.getSolutions().stream().count();
    }

    @Transactional
    public boolean deleteUser(Long id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        userRepository.delete(user);
        githubClient.revokeOauthToken(user.getAccessToken());
        return true;
    }

    public boolean verifyUsername(Long userId, String inputUsername) {
        String storedUsername = userRepository.findById(userId).get().getUsername();
        return storedUsername.equals(inputUsername);
    }
}
