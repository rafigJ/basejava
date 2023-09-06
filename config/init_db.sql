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



