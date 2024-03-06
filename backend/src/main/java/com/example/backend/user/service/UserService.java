package com.example.backend.user.service;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.BadRequestException;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.UserDTO.Profile;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
    public void deleteUser(Long id, String inputUsername) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

        if (verifyUsername(user.getUsername(), inputUsername)) {
            userRepository.delete(user);
        } else {
            throw new BadRequestException(ExceptionStatus.USERNAME_MISMATCH);
        }
    }

    private boolean verifyUsername(String storedUsername, String inputUsername) {
        return storedUsername.equals(inputUsername);
    }
}
