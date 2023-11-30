CREATE TABLE solutions (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT NOT NULL,
    problem_id BIGINT NOT NULL,
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT solutions_repository_id_fk FOREIGN KEY (repository_id) REFERENCES repositories (id),
    CONSTRAINT solutions_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);
