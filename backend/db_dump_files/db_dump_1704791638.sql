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
ALTER TABLE ONLY public.programmers_problem_details DROP CONSTRAINT programmers_problem_details_problem_id_fk;
ALTER TABLE ONLY public.problems DROP CONSTRAINT problems_provider_id_fk;
ALTER TABLE ONLY public.github_repositories DROP CONSTRAINT github_repositories_user_id_fk;
ALTER TABLE ONLY public.baekjoon_problem_details DROP CONSTRAINT baekjoon_problem_details_problem_id_fk;
DROP INDEX public.users_username_uindex;
DROP INDEX public.flyway_schema_history_s_idx;
ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
ALTER TABLE ONLY public.solutions DROP CONSTRAINT solutions_pkey;
ALTER TABLE ONLY public.providers DROP CONSTRAINT providers_pkey;
ALTER TABLE ONLY public.programmers_problem_details DROP CONSTRAINT programmers_problem_details_pkey;
ALTER TABLE ONLY public.problems DROP CONSTRAINT problems_pkey;
ALTER TABLE ONLY public.github_repositories DROP CONSTRAINT github_repositories_pkey;
ALTER TABLE ONLY public.flyway_schema_history DROP CONSTRAINT flyway_schema_history_pk;
ALTER TABLE ONLY public.baekjoon_problem_details DROP CONSTRAINT baekjoon_problem_details_pkey;
ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.solutions ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.providers ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.programmers_problem_details ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.problems ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.github_repositories ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.baekjoon_problem_details ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE public.users_id_seq;
DROP TABLE public.users;
DROP SEQUENCE public.solutions_id_seq;
DROP TABLE public.solutions;
DROP SEQUENCE public.providers_id_seq;
DROP TABLE public.providers;
DROP SEQUENCE public.programmers_problem_details_id_seq;
DROP TABLE public.programmers_problem_details;
DROP SEQUENCE public.problems_id_seq;
DROP TABLE public.problems;
DROP SEQUENCE public.github_repositories_id_seq;
DROP TABLE public.github_repositories;
DROP TABLE public.flyway_schema_history;
DROP SEQUENCE public.baekjoon_problem_details_id_seq;
DROP TABLE public.baekjoon_problem_details;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: baekjoon_problem_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.baekjoon_problem_details (
    id bigint NOT NULL,
    problem_id bigint NOT NULL,
    tier character varying(255) NOT NULL
);


ALTER TABLE public.baekjoon_problem_details OWNER TO postgres;

--
-- Name: baekjoon_problem_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.baekjoon_problem_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.baekjoon_problem_details_id_seq OWNER TO postgres;

--
-- Name: baekjoon_problem_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.baekjoon_problem_details_id_seq OWNED BY public.baekjoon_problem_details.id;


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
    token character varying(255) NOT NULL,
    url character varying(255) NOT NULL,
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
    provider_id bigint NOT NULL,
    title character varying(255) NOT NULL,
    content character varying(255) NOT NULL,
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
-- Name: programmers_problem_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.programmers_problem_details (
    id bigint NOT NULL,
    problem_id bigint NOT NULL,
    level integer NOT NULL
);


ALTER TABLE public.programmers_problem_details OWNER TO postgres;

--
-- Name: programmers_problem_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.programmers_problem_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.programmers_problem_details_id_seq OWNER TO postgres;

--
-- Name: programmers_problem_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.programmers_problem_details_id_seq OWNED BY public.programmers_problem_details.id;


--
-- Name: providers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.providers (
    id bigint NOT NULL,
    name character varying(255),
    created_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp(6) without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.providers OWNER TO postgres;

--
-- Name: providers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.providers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.providers_id_seq OWNER TO postgres;

--
-- Name: providers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.providers_id_seq OWNED BY public.providers.id;


--
-- Name: solutions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solutions (
    id bigint NOT NULL,
    github_repository_id bigint NOT NULL,
    problem_id bigint NOT NULL,
    language character varying(64) NOT NULL,
    source_code text NOT NULL,
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
-- Name: baekjoon_problem_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problem_details ALTER COLUMN id SET DEFAULT nextval('public.baekjoon_problem_details_id_seq'::regclass);


--
-- Name: github_repositories id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.github_repositories ALTER COLUMN id SET DEFAULT nextval('public.github_repositories_id_seq'::regclass);


--
-- Name: problems id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problems ALTER COLUMN id SET DEFAULT nextval('public.problems_id_seq'::regclass);


--
-- Name: programmers_problem_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problem_details ALTER COLUMN id SET DEFAULT nextval('public.programmers_problem_details_id_seq'::regclass);


--
-- Name: providers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.providers ALTER COLUMN id SET DEFAULT nextval('public.providers_id_seq'::regclass);


--
-- Name: solutions id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solutions ALTER COLUMN id SET DEFAULT nextval('public.solutions_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: baekjoon_problem_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.baekjoon_problem_details (id, problem_id, tier) FROM stdin;
1	6	sliver2
2	7	bronze1
3	8	sliver3
4	10	gold3
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init	SQL	V1__init.sql	1861848988	postgres	2023-12-12 22:06:34.441083	15	t
\.


--
-- Data for Name: github_repositories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.github_repositories (id, token, url, user_id, created_at, updated_at) FROM stdin;
1	token-test	repo-url	1	2023-12-12 22:10:35.172135	2023-12-12 22:10:35.172135
2	token-test2	repo-url2	2	2024-01-06 17:59:30.992	2024-01-06 17:59:30.992
\.


--
-- Data for Name: problems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.problems (id, provider_id, title, content, created_at, updated_at) FROM stdin;
1	1	문제1	문제1	2023-12-12 22:10:00.453036	2023-12-12 22:10:00.453036
2	1	문제2	문제2	2023-12-12 22:10:14.78354	2023-12-12 22:10:14.78354
3	1	문제3	문제3	2024-01-09 18:01:20.659805	2024-01-09 18:01:20.659805
4	1	문제4	문제4	2024-01-09 18:01:39.759098	2024-01-09 18:01:39.759098
5	1	문제5	문제5	2024-01-09 18:01:48.490755	2024-01-09 18:01:48.490755
6	2	문제6	문제6	2024-01-09 18:01:59.703228	2024-01-09 18:01:59.703228
7	2	문제7	문제7	2024-01-09 18:02:09.113231	2024-01-09 18:02:09.113231
8	2	문제8	문제8	2024-01-09 18:02:19.338133	2024-01-09 18:02:19.338133
9	1	문제9	문제9	2024-01-09 18:02:29.255896	2024-01-09 18:02:29.255896
10	1	문제10	문제10	2024-01-09 18:02:42.543157	2024-01-09 18:02:42.543157
\.


--
-- Data for Name: programmers_problem_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.programmers_problem_details (id, problem_id, level) FROM stdin;
1	1	1
2	2	2
3	3	2
4	4	2
5	5	3
6	9	1
\.


--
-- Data for Name: providers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.providers (id, name, created_at, updated_at) FROM stdin;
1	programmers-test	2023-12-12 22:09:49.867257	2023-12-12 22:09:49.867257
2	baekjoon-test	2024-01-06 18:00:31.037	2024-01-06 18:00:31.037
\.


--
-- Data for Name: solutions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.solutions (id, github_repository_id, problem_id, language, source_code, created_at, updated_at) FROM stdin;
1	1	1	python	print('hello world')	2023-12-12 22:11:12.253582	2023-12-12 22:11:12.253582
2	1	2	java	System.out.println("hello world")	2023-12-12 22:11:38.791192	2023-12-12 22:11:38.791192
3	1	3	java	System.out.println("h");	2024-01-09 18:07:59.464584	2024-01-09 18:07:59.464584
4	1	4	java	System.out.println("i");	2024-01-09 18:08:11.775961	2024-01-09 18:08:11.775961
5	1	5	java	System.out.println("iam");	2024-01-09 18:08:26.093284	2024-01-09 18:08:26.093284
6	2	6	java	System.out.println("j");	2024-01-09 18:09:06.112449	2024-01-09 18:09:06.112449
7	2	7	c	printf("hello world");	2024-01-09 18:09:44.091106	2024-01-09 18:09:44.091106
8	2	8	c	printf("test");	2024-01-09 18:10:05.927734	2024-01-09 18:10:05.927734
9	2	9	java	public class {}	2024-01-09 18:10:55.392993	2024-01-09 18:10:55.392993
10	1	10	mysql	SELECT * FROM USERS	2024-01-09 18:11:47.900472	2024-01-09 18:11:47.900472
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, github_url, profile_image_url, username, roles, created_at, updated_at, deleted_at) FROM stdin;
1	장기하	https://github.com/giha	https://i.namu.wiki/i/ZnBMAAGJaiFKqDmASXCt-977Xuq6gLA-G8AsD4K1BKCVBEzrjISoW9QyfcSKPnacwuBpCGSSyBtCJv8E-UocNQ.webp	giha0123	["ADMIN"]	2023-12-12 22:09:29.365717	2023-12-12 22:09:29.365717	\N
2	장기준	https://github.com/gijun	https://i.namu.wiki/i/JbBMAAGJaiFKqDmASXCt-977Xuq6gLA-G8AsD4K1BKCVBEzrjISoW9QyfcSKPnacwuBpCGSSyBtCJv8E-UocNQ.webp	gijun0123	["ADMIN"]	2024-01-06 17:58:56.221	2024-01-06 17:58:56.221	\N
\.


--
-- Name: baekjoon_problem_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.baekjoon_problem_details_id_seq', 1, false);


--
-- Name: github_repositories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.github_repositories_id_seq', 1, false);


--
-- Name: problems_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.problems_id_seq', 1, false);


--
-- Name: programmers_problem_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.programmers_problem_details_id_seq', 1, false);


--
-- Name: providers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.providers_id_seq', 1, false);


--
-- Name: solutions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.solutions_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: baekjoon_problem_details baekjoon_problem_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problem_details
    ADD CONSTRAINT baekjoon_problem_details_pkey PRIMARY KEY (id);


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
-- Name: programmers_problem_details programmers_problem_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problem_details
    ADD CONSTRAINT programmers_problem_details_pkey PRIMARY KEY (id);


--
-- Name: providers providers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.providers
    ADD CONSTRAINT providers_pkey PRIMARY KEY (id);


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
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- Name: baekjoon_problem_details baekjoon_problem_details_problem_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.baekjoon_problem_details
    ADD CONSTRAINT baekjoon_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES public.problems(id);


--
-- Name: github_repositories github_repositories_user_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.github_repositories
    ADD CONSTRAINT github_repositories_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: problems problems_provider_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.problems
    ADD CONSTRAINT problems_provider_id_fk FOREIGN KEY (provider_id) REFERENCES public.providers(id);


--
-- Name: programmers_problem_details programmers_problem_details_problem_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.programmers_problem_details
    ADD CONSTRAINT programmers_problem_details_problem_id_fk FOREIGN KEY (problem_id) REFERENCES public.problems(id);


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

