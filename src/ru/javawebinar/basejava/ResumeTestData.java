package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Александр Соколов");
        resume.addInfoAtSection(SectionType.PERSONAL, "Я считаю, что мои личные качества включают в себя ответственность, настойчивость и адаптивность. Я всегда стремлюсь брать на себя ответственность за свои действия и принимать решения, основанные на разумных обстоятельствах. Настойчивость помогает мне преодолевать препятствия и достигать поставленных целей, несмотря на трудности. Я также гибко адаптируюсь к новым ситуациям и быстро учусь, что позволяет мне эффективно работать в различных обстоятельствах.");
        resume.addInfoAtSection(SectionType.OBJECTIVE, "Моя позиция в настоящее время заключается в том, что я ищу возможность занять позицию, связанную с моими навыками и интересами. Я стремлюсь найти работу, где смогу проявить свои способности и внести значимый вклад в команду или организацию. Я открыт для различных возможностей и готов рассмотреть предложения, которые позволят мне развиваться профессионально и достигать новых высот.");

        resume.addInfoAtSection(SectionType.ACHIEVEMENT, "Участие в международной конференции: Я был выбран для презентации на международной конференции, где представил свои исследования и получил положительные отзывы от экспертов в своей области.");
        resume.addInfoAtSection(SectionType.ACHIEVEMENT, "Премия за лидерство: Моя команда признала мое влияние и лидерские качества, и я получил премию за лучшего лидера внутри организации.");
        resume.addInfoAtSection(SectionType.ACHIEVEMENT, "Опубликованные статьи: Мои исследования были опубликованы в рецензируемых научных журналах, что подтверждает мою экспертность в выбранной области.");

        resume.addInfoAtSection(SectionType.QUALIFICATIONS, "Бакалавр наук в области компьютерных наук: Я успешно завершил программу бакалавриата в области компьютерных наук с отличными результатами, демонстрируя глубокое понимание теории и практических навыков в программировании и разработке программного обеспечения.");
        resume.addInfoAtSection(SectionType.QUALIFICATIONS, "Сертификаты профессионального развития: Я получил ряд сертификатов, связанных с моей областью специализации, таких как сертификат по анализу данных и сертификат по управлению проектами, что демонстрирует мою непрерывную жажду обучения и стремление к профессиональному росту.");
        resume.addInfoAtSection(SectionType.QUALIFICATIONS, "Рабочий опыт: Я имею опыт работы в крупной технологической компании, где я занимал должность разработчика программного обеспечения и успешно выполнял задачи в рамках проектов высокой сложности.");

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

        r.addInfoAtSection(SectionType.EXPERIENCE, "10/2014;01/2016;Старший разработчик (backend);Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        r.addInfoAtSection(SectionType.EXPERIENCE, "04/2012;10/2014;Java архитектор;Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        r.addInfoAtSection(SectionType.EXPERIENCE, "12/2010;04/2012;Ведущий программист;Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");

        System.out.println(r.getFullName());

        for (Map.Entry<ContactType, String> s : r.getAllContactInfo().entrySet()) {
            System.out.println(s.getKey() + ":" + s.getValue());
        }
        for (Map.Entry<SectionType, Section> info : r.getAllSectionInfo().entrySet()) {
            System.out.println(info.getKey() + ":" + info.getValue().getInsideData());
        }
    }
}
