--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4
-- Dumped by pg_dump version 15.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE ONLY public.solutions DROP CONSTRAINT solutions_problem_id_fk;
ALTER TABLE ONLY public.solutions DROP CONSTRAINT solutions_github_repository_id_fk;
ALTER TABLE ONLY public.programmers_problems DROP CONSTRAINT programmers_problems_problem_id_fk;
ALTER TABLE ONLY public.github_repositories DROP CONSTRAINT github_repositories_user_id_fk;
ALTER TABLE ONLY public.baekjoon_problems DROP CONSTRAINT baekjoon_problems_problem_id_fk;
DROP INDEX public.users_username_uindex;
DROP INDEX public.solutions_hash_key_uindex;
DROP INDEX public.programmers_problems_lesson_number_uindex;
DROP INDEX public.github_repositories_repo_uindex;
DROP INDEX public.flyway_schema_history_s_idx;
DROP INDEX public.baekjoon_problems_problem_number_uindex;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.solutions DROP CONSTRAINT solutions_pkey;
ALTER TABLE ONLY public.programmers_problems DROP CONSTRAINT programmers_problems_pkey;
ALTER TABLE ONLY public.problems DROP CONSTRAINT problems_pkey;
ALTER TABLE ONLY public.github_repositories DROP CONSTRAINT github_repositories_pkey;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
ALTER TABLE ONLY public.baekjoon_problems DROP CONSTRAINT baekjoon_problems_pkey;
ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.solutions ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.programmers_problems ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.problems ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.github_repositories ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.baekjoon_problems ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.users_id_seq;
DROP TABLE public.users;
DROP SEQUENCE public.solutions_id_seq;
DROP TABLE public.solutions;
DROP SEQUENCE public.programmers_problems_id_seq;
DROP TABLE public.programmers_problems;
DROP SEQUENCE public.problems_id_seq;
DROP TABLE public.problems;
DROP SEQUENCE public.github_repositories_id_seq;
DROP TABLE public.github_repositories;
DROP TABLE public.flyway_schema_history;
DROP SEQUENCE public.baekjoon_problems_id_seq;
DROP TABLE public.baekjoon_problems;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: baekjoon_problems; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.baekjoon_problems (
    id bigint NOT NULL,
    problem_number bigint NOT NULL,
    tier character varying(255) NOT NULL
);


ALTER TABLE public.baekjoon_problems OWNER TO postgres;

--
-- Name: baekjoon_problems_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.baekjoon_problems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.baekjoon_problems_id_seq OWNER TO postgres;

--
-- Name: baekjoon_problems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.baekjoon_problems_id_seq OWNED BY public.baekjoon_problems.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: github_repositories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.github_repositories (
    id bigint NOT NULL,
    repo character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    created_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.github_repositories OWNER TO postgres;

--
-- Name: github_repositories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.github_repositories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.github_repositories_id_seq OWNER TO postgres;

--
-- Name: github_repositories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.github_repositories_id_seq OWNED BY public.github_repositories.id;


--
-- Name: problems; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.problems (
    id bigint NOT NULL,
    type character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    created_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.problems OWNER TO postgres;

--
-- Name: problems_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.problems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.problems_id_seq OWNER TO postgres;

--
-- Name: problems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.problems_id_seq OWNED BY public.problems.id;


--
-- Name: programmers_problems; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.programmers_problems (
    id bigint NOT NULL,
    lesson_number bigint NOT NULL,
    level integer NOT NULL
);


ALTER TABLE public.programmers_problems OWNER TO postgres;

--
-- Name: programmers_problems_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.programmers_problems_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programmers_problems_id_seq OWNER TO postgres;

--
-- Name: programmers_problems_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.programmers_problems_id_seq OWNED BY public.programmers_problems.id;


--
-- Name: solutions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solutions (
    id bigint NOT NULL,
    github_repository_id bigint NOT NULL,
    problem_id bigint NOT NULL,
    language character varying(64) NOT NULL,
    source_code text NOT NULL,
    hash_key character varying(255) NOT NULL,
    created_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.solutions OWNER TO postgres;

--
-- Name: solutions_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.solutions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.solutions_id_seq OWNER TO postgres;

--
-- Name: solutions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.solutions_id_seq OWNED BY public.solutions.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    github_url character varying(255) NOT NULL,
    profile_image_url character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    roles character varying(255) DEFAULT ''::character varying NOT NULL,
    created_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    deleted_at timestamp(6) without time zone
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: baekjoon_problems id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problems ALTER COLUMN id SET DEFAULT nextval('public.baekjoon_problems_id_seq'::regclass);


--
-- Name: github_repositories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.github_repositories ALTER COLUMN id SET DEFAULT nextval('public.github_repositories_id_seq'::regclass);


--
-- Name: problems id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problems ALTER COLUMN id SET DEFAULT nextval('public.problems_id_seq'::regclass);


--
-- Name: programmers_problems id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problems ALTER COLUMN id SET DEFAULT nextval('public.programmers_problems_id_seq'::regclass);


--
-- Name: solutions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions ALTER COLUMN id SET DEFAULT nextval('public.solutions_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: baekjoon_problems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.baekjoon_problems (id, problem_number, tier) FROM stdin;
1	1000	Bronze
2	1001	Bronze
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init	SQL	V1__init.sql	-1620168637	postgres	2024-03-04 17:38:30.83135	15	t
\.


--
-- Data for Name: github_repositories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.github_repositories (id, repo, user_id, created_at, updated_at) FROM stdin;
1	youngjun0627/Rgorithm	1	2024-03-04 17:38:51.697399	2024-03-04 17:38:51.697429
\.


--
-- Data for Name: problems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.problems (id, type, title, created_at, updated_at) FROM stdin;
1	BaekjoonProblem	 A＋B	2024-03-04 17:40:44.657965	2024-03-04 17:40:44.657989
2	BaekjoonProblem	 A－B	2024-03-04 17:42:15.474532	2024-03-04 17:42:15.474561
3	ProgrammersProblem	 주사위 게임 3	2024-03-04 17:42:15.495622	2024-03-04 17:42:15.495644
4	ProgrammersProblem	 덧셈식 출력하기	2024-03-04 17:42:15.512507	2024-03-04 17:42:15.512521
5	ProgrammersProblem	 체육복	2024-03-04 17:42:15.518893	2024-03-04 17:42:15.518902
\.


--
-- Data for Name: programmers_problems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.programmers_problems (id, lesson_number, level) FROM stdin;
3	181916	0
4	181947	0
5	42862	1
\.


--
-- Data for Name: solutions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solutions (id, github_repository_id, problem_id, language, source_code, hash_key, created_at, updated_at) FROM stdin;
1	1	1	python	a, b = map(int, input().split())\r\nprint(a+b)	4f00838e0a46c192c02219f989edad606916b06a178bb9e8a2016d77945923da	2024-03-04 17:42:15.437661	2024-03-04 17:42:15.437698
2	1	2	python	a, b = map(int, input().split())\r\nprint(a-b)	befb29ad6119a1d81f021e4195ebdccfc1273d25b7695f1bc20f77d0ab061c77	2024-03-04 17:42:15.486252	2024-03-04 17:42:15.486276
3	1	3	cpp	#include <array>\n#include <algorithm>\n#include <cmath>\nusing namespace std;\n\nint solution(int a, int b, int c, int d) {\n    if(a == b && b == c && c == d)\n        return 1111 * a;\n    array<int, 4> arr{a, b, c, d};\n    sort(arr.begin(), arr.end());\n    auto [p, q, r, s] = arr;\n    if(p == q && r == s)\n        return (q + r) * (r - q);\n    if(q == r){\n        if(p == q || r == s){\n            int t = 11 * q + (p == q? s : p);\n            return t * t;\n        }\n        else\n            return p * s;\n    }\n    if(p == q || r == s)\n        return p == q? r * s : p * q;\n    return p;\n}	4e9ae60f0193cea95168f4d8efd32b1e97d23269ec01cdbdcde9c2c5226e81f0	2024-03-04 17:42:15.507613	2024-03-04 17:42:15.50763
4	1	4	python	a, b = map(int, input().strip().split(' '))\nprint(f"{a} + {b} = {a+b}")	3a29c7820f67d114a90d7964b1ab88148159f97077b5dfd2d6c42539157a0056	2024-03-04 17:42:15.515599	2024-03-04 17:42:15.515609
5	1	5	cpp	#include <vector>\nusing namespace std;\n\nint solution(int n, vector<int> lost, vector<int> reserve) {\n    int answer = n;\n\n    vector<int> uniform(n+2, 1);\n    for(int i = 0; i < lost.size(); i++)\n        uniform[lost[i]]--;\n    for(int i = 0; i < reserve.size(); i++)\n        uniform[reserve[i]]++;\n\n    for(int i = 1; i < n+1; i++)\n        if(uniform[i-1] == 0 && uniform[i] == 2) {\n            uniform[i-1]++;\n            uniform[i]--;\n        } else if(uniform[i+1] == 0) {\n            uniform[i]--;\n            uniform[i+1]++;\n        }\n\n    for(int i = 1; i <= n; i++)\n        if(uniform[i] == 0)\n            answer--;\n\n    return answer;\n}	bbe642162810d4575d508dce0e4065dd5735449de5578bfbfe0014c20c405177	2024-03-04 17:42:15.520869	2024-03-04 17:42:15.520878
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, github_url, profile_image_url, username, roles, created_at, updated_at, deleted_at) FROM stdin;
1	Ada	https://github.com/youngjun0627	https://avatars.githubusercontent.com/u/57058726?v=4	youngjun0627	null	2024-03-04 17:38:44.80299	2024-03-04 17:38:44.803014	\N
\.


--
-- Name: baekjoon_problems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.baekjoon_problems_id_seq', 1, false);


--
-- Name: github_repositories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.github_repositories_id_seq', 1, true);


--
-- Name: problems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.problems_id_seq', 5, true);


--
-- Name: programmers_problems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.programmers_problems_id_seq', 1, false);


--
-- Name: solutions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solutions_id_seq', 5, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, true);


--
-- Name: baekjoon_problems baekjoon_problems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problems
    ADD CONSTRAINT baekjoon_problems_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: github_repositories github_repositories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.github_repositories
    ADD CONSTRAINT github_repositories_pkey PRIMARY KEY (id);


--
-- Name: problems problems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problems
    ADD CONSTRAINT problems_pkey PRIMARY KEY (id);


--
-- Name: programmers_problems programmers_problems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problems
    ADD CONSTRAINT programmers_problems_pkey PRIMARY KEY (id);


--
-- Name: solutions solutions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: baekjoon_problems_problem_number_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX baekjoon_problems_problem_number_uindex ON public.baekjoon_problems USING btree (problem_number);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: github_repositories_repo_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX github_repositories_repo_uindex ON public.github_repositories USING btree (repo);


--
-- Name: programmers_problems_lesson_number_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX programmers_problems_lesson_number_uindex ON public.programmers_problems USING btree (lesson_number);


--
-- Name: solutions_hash_key_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX solutions_hash_key_uindex ON public.solutions USING btree (hash_key);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- Name: baekjoon_problems baekjoon_problems_problem_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problems
    ADD CONSTRAINT baekjoon_problems_problem_id_fk FOREIGN KEY (id) REFERENCES public.problems(id);


--
-- Name: github_repositories github_repositories_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.github_repositories
    ADD CONSTRAINT github_repositories_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: programmers_problems programmers_problems_problem_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problems
    ADD CONSTRAINT programmers_problems_problem_id_fk FOREIGN KEY (id) REFERENCES public.problems(id);


--
-- Name: solutions solutions_github_repository_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_github_repository_id_fk FOREIGN KEY (github_repository_id) REFERENCES public.github_repositories(id);


--
-- Name: solutions solutions_problem_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions
    ADD CONSTRAINT solutions_problem_id_fk FOREIGN KEY (problem_id) REFERENCES public.problems(id);


--
-- PostgreSQL database dump complete
--

