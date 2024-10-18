--- Table: public.users
-- DROP SEQUENCE IF EXISTS public.users_user_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.users_user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.users_user_id_seq
    OWNER TO postgres;

-- DROP TABLE IF EXISTS public.users;
CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL DEFAULT nextval('users_user_id_seq'::regclass),
    username text COLLATE pg_catalog."default"  UNIQUE NOT NULL,
    email text COLLATE pg_catalog."default" UNIQUE,
    password_user text COLLATE pg_catalog."default",
    created date,
    updated date,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;