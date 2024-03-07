CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    github_url        VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255) NOT NULL,
    username          VARCHAR(255) NOT NULL,
    roles             VARCHAR(255) NOT NULL DEFAULT '',
    created_at        TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at        TIMESTAMP(6)
);

CREATE TABLE github_repositories
(
    id         BIGSERIAL PRIMARY KEY,
    repo       VARCHAR(255) NOT NULL,
    user_id    BIGINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT github_repositories_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE problems
(
    id         BIGSERIAL PRIMARY KEY,
    type       VARCHAR(255) NOT NULL,
    title      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE programmers_problems
(
    id            BIGSERIAL PRIMARY KEY,
    lesson_number BIGINT NOT NULL,
    level         INTEGER NOT NULL,
    CONSTRAINT programmers_problems_problem_id_fk FOREIGN KEY (id) REFERENCES problems (id)
);

CREATE TABLE baekjoon_problems
(
    id            BIGSERIAL PRIMARY KEY,
    problem_number BIGINT NOT NULL,
    tier          VARCHAR(255) NOT NULL,
    CONSTRAINT baekjoon_problems_problem_id_fk FOREIGN KEY (id) REFERENCES problems (id)
);

CREATE TABLE solutions
(
    id                   BIGSERIAL PRIMARY KEY,
    github_repository_id BIGINT NOT NULL,
    problem_id           BIGINT NOT NULL,
    language             VARCHAR(64) NOT NULL,
    source_code          TEXT NOT NULL,
    hash_key             VARCHAR(255) NOT NULL,
    created_at           TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT solutions_github_repository_id_fk FOREIGN KEY (github_repository_id) REFERENCES github_repositories (id),
    CONSTRAINT solutions_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);

CREATE UNIQUE INDEX users_username_uindex ON users (username);
CREATE UNIQUE INDEX github_repositories_repo_uindex ON github_repositories (repo);
CREATE UNIQUE INDEX solutions_hash_key_uindex ON solutions (hash_key);
CREATE UNIQUE INDEX programmers_problems_lesson_number_uindex ON programmers_problems (lesson_number);
CREATE UNIQUE INDEX baekjoon_problems_problem_number_uindex ON baekjoon_problems (problem_number);
