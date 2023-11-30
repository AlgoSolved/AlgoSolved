package com.example.backend.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "repositories")
public class Repository extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: user_id

  // TODO: url

  // TODO: token
  @OneToMany(mappedBy = "repository")
  private List<Solution> solutions = new ArrayList<>();
}
