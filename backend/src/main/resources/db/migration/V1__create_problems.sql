CREATE TABLE problems (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    created_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);
