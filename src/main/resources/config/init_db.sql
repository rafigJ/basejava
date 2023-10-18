-- Table: public.resume

-- DROP TABLE IF EXISTS public.resume;

CREATE TABLE IF NOT EXISTS public.resume
(
    uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    full_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT resume_pkey PRIMARY KEY (uuid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.resume
    OWNER to postgres;

-- Table: public.contact

-- DROP TABLE IF EXISTS public.contact;

CREATE TABLE IF NOT EXISTS public.contact
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    value text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT contact_pk PRIMARY KEY (id),
    CONSTRAINT contact_resume_uuid_fkey FOREIGN KEY (resume_uuid)
        REFERENCES public.resume (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.contact
    OWNER to postgres;
-- Index: contact_uuid_type_index

-- DROP INDEX IF EXISTS public.contact_uuid_type_index;

CREATE UNIQUE INDEX IF NOT EXISTS contact_uuid_type_index
    ON public.contact USING btree
    (resume_uuid COLLATE pg_catalog."default" ASC NULLS LAST, type COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;


-- Table: public.section

-- DROP TABLE IF EXISTS public.section;

CREATE TABLE IF NOT EXISTS public.section
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    resume_uuid character(36) COLLATE pg_catalog."default" NOT NULL,
    type text COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT section_pk PRIMARY KEY (id),
    CONSTRAINT section_resume_uuid_fk FOREIGN KEY (resume_uuid)
        REFERENCES public.resume (uuid) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE CASCADE
)
TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.section
    OWNER to postgres;

COMMENT ON CONSTRAINT section_pk ON public.section
    IS 'id';
-- Index: section_uuid_type_index

-- DROP INDEX IF EXISTS public.section_uuid_type_index;

CREATE UNIQUE INDEX IF NOT EXISTS section_uuid_type_index
    ON public.section USING btree
    (resume_uuid COLLATE pg_catalog."default" ASC NULLS LAST, type COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default;


INSERT INTO public.resume(
	uuid, full_name)
	VALUES ('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'Кислин');

INSERT INTO public.section(
	resume_uuid, type, content)
	VALUES ('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'PERSONAL', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"text":"Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"}}'),
	('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'QUALIFICATIONS', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"list":["JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2","Version control: Subversion, Git, Mercury, ClearCase, Perforce","DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB","Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy"]}}'),
	('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'ACHIEVEMENT', '{"CLASSNAME":"ru.javawebinar.basejava.model.ListSection","INSTANCE":{"list":["Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет","С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.","Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.","Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.","Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.","Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).","Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."]}}'),
	('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'OBJECTIVE', '{"CLASSNAME":"ru.javawebinar.basejava.model.TextSection","INSTANCE":{"text":"Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."}}'),
	('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'EDUCATION', '{"CLASSNAME":"ru.javawebinar.basejava.model.CompanySection","INSTANCE":{"companies":[{"companyName":"Coursera","website":"","periods":[{"startDate":{"year":2013,"month":3,"day":3},"endDate":{"year":2013,"month":5,"day":3},"title":"Functional Programming Principles in Scala\u0027 by Martin Odersky","description":""}]},{"companyName":"Luxoft","website":"","periods":[{"startDate":{"year":2011,"month":3,"day":3},"endDate":{"year":2011,"month":4,"day":3},"title":"Курс Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.","description":""}]}]}}'),
	('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'EXPERIENCE', '{"CLASSNAME":"ru.javawebinar.basejava.model.CompanySection","INSTANCE":{"companies":[{"companyName":"Java Online Projects","website":"http://javaops.ru/","periods":[{"startDate":{"year":2013,"month":10,"day":3},"endDate":{"year":2023,"month":10,"day":10},"title":"Автор проекта.","description":"Создание, организация и проведение Java онлайн проектов и стажировок."}]},{"companyName":"Wrike","website":"https://www.wrike.com/","periods":[{"startDate":{"year":2014,"month":10,"day":3},"endDate":{"year":2016,"month":1,"day":3},"title":"Старший разработчик (backend)","description":"Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."}]},{"companyName":"RIT Center","website":"https://stamina-online.com/ru","periods":[{"startDate":{"year":2012,"month":4,"day":3},"endDate":{"year":2014,"month":10,"day":3},"title":"Java архитектор","description":"Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"}]},{"companyName":"Luxoft (Deutsche Bank)","website":"","periods":[{"startDate":{"year":2010,"month":12,"day":3},"endDate":{"year":2012,"month":4,"day":3},"title":"Ведущий программист","description":"Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."},{"startDate":{"year":2012,"month":12,"day":3},"endDate":{"year":2026,"month":4,"day":3},"title":"Не ведущий","description":"Большой текст Тест большего количества периодов "},{"startDate":{"year":2026,"month":12,"day":3},"endDate":{"year":2036,"month":4,"day":3},"title":"Не ведущий","description":"Большой текст2 Тест большего количества периодов2 "}]}]}}');

INSERT INTO public.contact(
	resume_uuid, type, value
) VALUES('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'PHONE_NUMBER', '+7(921) 855-0482'),
('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'EMAIL', 'gkislin@yandex.ru'),
('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'LINKEDIN', 'Профиль linkedin'),
('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'SKYPE', 'grigory.kislin'),
('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'STACKOVERFLOW', 'https://stackoverflow.com/users/548473'),
('9b4bea09-2d9d-409f-bd9d-6762480cd269', 'HOMEPAGE', 'http://gkislin.ru/');
