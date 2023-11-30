CREATE TABLE programmers_problem_details (
    id BIGSERIAL PRIMARY KEY,
    problem_id BIGINT NOT NULL,
    level INTEGER NOT NULL,
    CONSTRAINT programmers_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);
