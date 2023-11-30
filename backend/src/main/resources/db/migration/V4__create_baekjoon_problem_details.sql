CREATE TABLE baekjoon_problem_details (
    id BIGSERIAL PRIMARY KEY,
    problem_id BIGINT NOT NULL,
    tier VARCHAR(255) NOT NULL,
    CONSTRAINT baekjoon_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);
