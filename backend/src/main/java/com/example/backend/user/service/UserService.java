package com.example.backend.user.service;

import com.example.backend.common.enums.ExceptionStatus;
import com.example.backend.common.exceptions.NotFoundException;
import com.example.backend.github.domain.GithubRepository;
import com.example.backend.user.domain.User;
import com.example.backend.user.dto.UserDTO;
import com.example.backend.user.repository.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserDTO getUserInfo(Long id) {
    User user = userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));

    return UserDTO.mapToDTO(user, getSolutionCount(user.getGithubRepository()));
  }

  public Long getSolutionCount(GithubRepository githubRepository) {
    return githubRepository.getSolutions().stream().count();
  }

  @Transactional
  public boolean deleteUser(Long id) {
    return userRepository.findById(id)
        .map(user -> {
          userRepository.delete(user);
          return true;
        })
        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND));
  }
}
