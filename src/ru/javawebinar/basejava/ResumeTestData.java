package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume r = new Resume("Григорий Кислин");
        r.addContactInfo(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        r.addContactInfo(ContactType.SKYPE, "skype:grigory.kislin");
        r.addContactInfo(ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContactInfo(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r.addContactInfo(ContactType.GITHUB, "https://github.com/gkislin");
        r.addContactInfo(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        r.addContactInfo(ContactType.HOMEPAGE, "http://gkislin.ru/");

        r.addInfoAtSection(SectionType.PERSONAL, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        r.addInfoAtSection(SectionType.OBJECTIVE, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        r.addInfoAtSection(SectionType.QUALIFICATIONS, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");

        Company company = new Company("Java Online Projects");
        company.addPeriodAtList(parse("10/2013"), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Wrike");
        company.addPeriodAtList(parse("10/2014"), parse("01/2016"), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("RIT Center");
        company.addPeriodAtList(parse("04/2012"), parse("10/2014"), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Luxoft (Deutsche Bank)");
        company.addPeriodAtList(parse("12/2010"), parse("04/2012"), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Coursera");
        company.addPeriodAtList(parse("03/2013"), parse("05/2013"), "'Functional Programming Principles in Scala' by Martin Odersky", null);
        r.addInfoAtSection(SectionType.EDUCATION, company);

        company = new Company("Coursera");
        company.addPeriodAtList(parse("03/2013"), parse("05/2013"), "'Functional Programming Principles in Scala' by Martin Odersky", null);
        r.addInfoAtSection(SectionType.EDUCATION, company);

        company = new Company("Luxoft");
        company.addPeriodAtList(parse("03/2011"), parse("04/2011"), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null);
        r.addInfoAtSection(SectionType.EDUCATION, company);


        System.out.println(r.getFullName());

        for (ContactType c:ContactType.values()) {
            System.out.println(c.getTitle() + ':' + r.getContactInfo(c));
        }
        for (SectionType s: SectionType.values()) {
            System.out.println(s.getTitle() + ':' + r.getSection(s));
        }
    }

    private static LocalDate parse(String monthYear){
        String[] str = monthYear.split("/");
        if (str.length != 2) {
            throw new RuntimeException(monthYear + "must be in format MM/YYYY");
        }
        return LocalDate.parse(str[1].concat("-"+str[0]).concat("-03"));
    }
}
