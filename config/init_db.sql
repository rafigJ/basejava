-- auto-generated definition
CREATE TABLE public.resume
(
    uuid      char(36) NOT NULL
        PRIMARY KEY,
    full_name TEXT     NOT NULL
);

ALTER TABLE public.resume
    OWNER TO postgres;

CREATE TABLE contact
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY
        CONSTRAINT contact_pk
            PRIMARY KEY,
    resume_uuid CHAR(36) NOT NULL REFERENCES resume ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

ALTER TABLE contact
    OWNER TO postgres;

CREATE UNIQUE INDEX contact_uuid_type_index
    ON public.contact (resume_uuid, type);


-- auto-generated definition
CREATE TABLE section
(
    id          integer GENERATED ALWAYS AS IDENTITY
        CONSTRAINT section_pk
            PRIMARY KEY,
    resume_uuid char(36) NOT NULL
        CONSTRAINT section_resume_uuid_fk
            REFERENCES resume
            ON UPDATE RESTRICT ON DELETE CASCADE,
    type        text     NOT NULL,
    value       text     NOT NULL
);

COMMENT ON CONSTRAINT section_pk ON section IS 'id';

ALTER TABLE section
    OWNER TO postgres;

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, type);


