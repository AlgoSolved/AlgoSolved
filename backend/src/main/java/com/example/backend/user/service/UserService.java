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

  public UserDTO getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);

    if (user == null) {
      throw new NotFoundException(ExceptionStatus.USER_NOT_FOUND);
    }
    return UserDTO.mapToDTO(user, getSolutionCount(user.getGithubRepository()));
  }

  public Long getSolutionCount(GithubRepository githubRepository) {
    return githubRepository.getSolutions().stream().count();
  }

  @Transactional
  public boolean deleteUser(String username) {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      userRepository.delete(user);
      return true;
    }
    return false;
  }
}

