package com.example.backend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActionApplicationTests {

  @Autowired
  MatchController matchController;

  @Test
  @DisplayName("Get Match")
  void matchTest() {
    Assertions.assertThat(matchController.match()).isEqualTo("match");
  }

  @Test
  @DisplayName("Get World")
  void worldTest() {
    Assertions.assertThat(matchController.world()).isEqualTo("");
  }
}
