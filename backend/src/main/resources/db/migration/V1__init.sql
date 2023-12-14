CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    github_url        VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255) NOT NULL,
    username          VARCHAR(255) NOT NULL,
    roles             VARCHAR(255) NOT NULL DEFAULT '',
    created_at        timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at        timestamp(6)
);

CREATE TABLE github_repositories
(
    id         BIGSERIAL PRIMARY KEY,
    token      VARCHAR(255) NOT NULL,
    url        VARCHAR(255) NOT NULL,
    user_id    BIGINT       NOT NULL,
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT github_repositories_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE providers
(
    id         BIGSERIAL PRIMARY KEY,
    name       varchar(255),
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE problems
(
    id          BIGSERIAL PRIMARY KEY,
    provider_id BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    content     VARCHAR(255) NOT NULL,
    created_at  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT problems_provider_id_fk FOREIGN KEY (provider_id) REFERENCES providers (id)
);

CREATE TABLE programmers_problem_details
(
    id         BIGSERIAL PRIMARY KEY,
    problem_id BIGINT  NOT NULL,
    level      INTEGER NOT NULL,
    CONSTRAINT programmers_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);

CREATE TABLE baekjoon_problem_details
(
    id         BIGSERIAL PRIMARY KEY,
    problem_id BIGINT       NOT NULL,
    tier       VARCHAR(255) NOT NULL,
    CONSTRAINT baekjoon_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);

CREATE TABLE solutions
(
    id                   BIGSERIAL PRIMARY KEY,
    github_repository_id BIGINT       NOT NULL,
    problem_id           BIGINT       NOT NULL,
    language             VARCHAR(64) NOT NULL,
    source_code          TEXT         NOT NULL,
    created_at           timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at           timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT solutions_github_repository_id_fk FOREIGN KEY (github_repository_id) REFERENCES github_repositories (id),
    CONSTRAINT solutions_problem_id_fk FOREIGN KEY (problem_id) REFERENCES problems (id)
);

CREATE UNIQUE INDEX users_username_uindex ON users (username);

