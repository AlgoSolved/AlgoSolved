package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
  @GetMapping("/match")
  public String match() {
    return "match";
  }

  @GetMapping("/world")
  public String world() {
    return "world";
  }



}
