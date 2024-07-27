package org.algosolved.backend.problem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "programmers_problems")
@Getter
@Setter
@DiscriminatorValue("ProgrammersProblem")
public class ProgrammersProblem extends Problem {
    @Column(name = "lesson_number")
    private Long lessonNumber;

    @Column(name = "level")
    private Integer level;

    public ProgrammersProblem() {}

    @Builder
    public ProgrammersProblem(String title, Long lessonNumber, Integer level) {
        super(title);
        this.lessonNumber = lessonNumber;
        this.level = level;
    }

    public String getLink() {
        return "https://programmers.co.kr/learn/courses/30/lessons/" + this.getLessonNumber();
    }

    public String getRank() {
        return this.level.toString();
    }

    public Long getNumber() {
        return this.lessonNumber;
    }
}
