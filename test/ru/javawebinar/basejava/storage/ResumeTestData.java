package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Company;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.time.LocalDate;
import java.util.Random;

public class ResumeTestData {

    public static Resume getFullRandomResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        addContacts(resume);
        addSectionData(resume, true);
        return resume;
    }

    public static Resume getNotFullRandomResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        addContacts(resume);
        addSectionData(resume, false);
        return resume;
    }

    private static void addContacts(Resume r) {
        r.addContactInfo(ContactType.PHONE_NUMBER, getRandomNum());
        r.addContactInfo(ContactType.SKYPE, getRandomString(10));
        r.addContactInfo(ContactType.EMAIL, getRandomString(10));
        r.addContactInfo(ContactType.LINKEDIN, getRandomString(10));
        r.addContactInfo(ContactType.GITHUB, getRandomString(10));
        r.addContactInfo(ContactType.STACKOVERFLOW, getRandomString(10));
        r.addContactInfo(ContactType.HOMEPAGE, getRandomString(10));
    }

    private static void addSectionData(Resume r, boolean isFull) {
        r.addInfoAtSection(SectionType.PERSONAL, getRandomString(200));
        r.addInfoAtSection(SectionType.OBJECTIVE, getRandomString(200));

        for (int i = 0; i < 4; i++) {
            r.addInfoAtSection(SectionType.ACHIEVEMENT, getRandomString(200));
            r.addInfoAtSection(SectionType.QUALIFICATIONS, getRandomString(200));

            Company company = new Company(
                    getRandomString(20),
                    getRandomStringOrNull(isFull)
            );

            LocalDate date = getRandomTime();
            company.getPeriods().add(
                    new Company.Period(date,
                            date.plusYears(2),
                            getRandomString(16),
                            getRandomString(100))
            );
            r.addInfoAtSection(SectionType.EXPERIENCE, company);

            company = new Company(getRandomString(20));
            date = getRandomTime();
            String description = getRandomStringOrNull(isFull);
            company.getPeriods().add(new Company.Period(date, date.plusYears(2), getRandomString(16),
                    description));
            r.addInfoAtSection(SectionType.EDUCATION, company);
        }
    }

    private static String getRandomStringOrNull(boolean isFull) {
        return isFull ? getRandomString(100) : null;
    }

    private static String getRandomNum() {
        int leftLimit = 48; // letter '1'
        int rightLimit = 57; // letter '9'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static String getRandomString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static LocalDate getRandomTime() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
        int year = random.nextInt(120) + 1970;
        return LocalDate.of(year, month, random.nextInt(9) + 1);
    }

    public static void main(String[] args) {
        Resume r = getResume();


        System.out.println(r.getFullName());

        for (ContactType c : ContactType.values()) {
            System.out.println(c.getTitle() + ':' + r.getContactInfo(c));
        }
        for (SectionType s : SectionType.values()) {
            System.out.println(s.getTitle() + ':' + r.getSection(s));
        }
    }

    public static Resume getResume() {
        Resume r = new Resume("Григорий Кислин");

        r.addInfoAtSection(SectionType.PERSONAL, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        r.addInfoAtSection(SectionType.OBJECTIVE, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        String[] achievements = {"Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."};
        r.addInfoAtSection(SectionType.ACHIEVEMENT, achievements);
        r.addInfoAtSection(SectionType.ACHIEVEMENT, "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        r.addInfoAtSection(SectionType.QUALIFICATIONS, "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        r.addInfoAtSection(SectionType.QUALIFICATIONS, "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");

        Company company = new Company("Java Online Projects");
        company.getPeriods().add(new Company.Period(parse("10/2013"), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Wrike");
        company.getPeriods().add(new Company.Period(parse("10/2014"), parse("01/2016"),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("RIT Center");
        company.getPeriods().add(new Company.Period(parse("04/2012"), parse("10/2014"),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: " +
                        "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы " +
                        "(pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, " +
                        "1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                        "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Luxoft (Deutsche Bank)");
        company.getPeriods().add(new Company.Period(parse("12/2010"), parse("04/2012"), "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                        "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        r.addInfoAtSection(SectionType.EXPERIENCE, company);

        company = new Company("Coursera");
        company.getPeriods().add(new Company.Period(parse("03/2013"), parse("05/2013"),
                "'Functional Programming Principles in Scala' by Martin Odersky", null));
        r.addInfoAtSection(SectionType.EDUCATION, company);

        company = new Company("Coursera");
        company.getPeriods().add(new Company.Period(parse("03/2013"), parse("05/2013"),
                "'Functional Programming Principles in Scala' by Martin Odersky", null));
        r.addInfoAtSection(SectionType.EDUCATION, company);

        company = new Company("Luxoft");
        company.getPeriods().add(new Company.Period(parse("03/2011"), parse("04/2011"),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", null));
        r.addInfoAtSection(SectionType.EDUCATION, company);
        return r;
    }

    private static LocalDate parse(String monthYear) {
        String[] str = monthYear.split("/");
        if (str.length != 2) {
            throw new RuntimeException(monthYear + "must be in format MM/YYYY");
        }
        return LocalDate.parse(str[1].concat("-" + str[0]).concat("-03"));
    }
}
