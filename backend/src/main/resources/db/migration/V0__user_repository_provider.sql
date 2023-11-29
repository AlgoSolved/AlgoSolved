CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    github_url VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    repository_id BIGINT,
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_repository_id_fk FOREIGN KEY (repository_id) REFERENCES repositories (id)
);

CREATE TABLE repositories (
    id BIGINT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    user_id BIGINT,
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT repositories_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE providers (
    id BIGINT PRIMARY KEY,
    name varchar(255),
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
