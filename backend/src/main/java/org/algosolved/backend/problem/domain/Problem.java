package org.algosolved.backend.problem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.algosolved.backend.common.BaseTimeEntity;
import org.algosolved.backend.solution.domain.Solution;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "problems")
@Getter
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", columnDefinition = "CHAR")
public abstract class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    protected String title;

    @OneToMany(mappedBy = "problem")
    private List<Solution> solutions;

    public Problem(String title) {
        this.title = title;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public abstract String getLink();

    public abstract String getRank();

    public abstract Long getNumber();
}
