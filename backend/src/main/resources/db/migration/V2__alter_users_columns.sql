ALTER TABLE users ADD uuid BIGINT;
ALTER TABLE users ADD github_repository_id BIGINT;

ALTER TABLE users DROP COLUMN repository_id;
ALTER TABLE users DROP COLUMN content;

ALTER TABLE users ALTER COLUMN deleted_at DROP NOT NULL;

ALTER TABLE baekjoon_problem_details RENAME TO beakjoon_problem_details;
