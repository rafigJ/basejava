package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.util.List;
import java.util.Map;


public class HtmlMapper {
    public static String toHtml(Map.Entry<ContactType, String> contactEntry) {
        return toHtml(contactEntry.getKey(), contactEntry.getValue());
    }

    public static String sectionToHtml(Map.Entry<SectionType, Section> sectionEntry) {
        return toHtml(sectionEntry.getKey(), sectionEntry.getValue());
    }

    public static String toHtml(ContactType ct, String value) {
        if (value == null) {
            return "";
        }
        switch (ct) {
            case EMAIL:
                return "<a href='mailto:" + value + "'>" + value + "</a>\n";
            default:
                return ct.getTitle() + ": " + value + "\n";
        }
    }

    public static String toHtml(SectionType st, Section section) {
        StringBuilder html = new StringBuilder();
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection ts = ((TextSection) section);
                return String.format("<div><h3>%s</h3></div>\n%s", st.getTitle(), ts.getText());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = ((ListSection) section);
                html.append(String.format("<div><h3>%s</h3></div>\n<div><ul>\n", st.getTitle()));
                for (String s : ls.getList()) {
                    html.append("<li>")
                            .append(s)
                            .append("</li>\n");
                }
                html.append("</ul>\n</div><br/>");
                return html.toString();
            case EXPERIENCE:
            case EDUCATION:
                CompanySection cs = ((CompanySection) section);
                html.append(String.format("<div><h3>%s</h3></div><div>", st.getTitle()));

                for (Company c : cs.getCompanies()) {
                    html.append("<h4>");
                    if (c.hasWebsite()) {
                        html.append(getHyperLink(c.getCompanyName(), c.getWebsite()));
                    } else {
                        html.append(c.getCompanyName());
                    }
                    html.append("</h4>\n");

                    for (Company.Period period : c.getPeriods()) {
                        html.append("<div class='period-section'> \n");
                        html.append(String.format("<div class='period-dates'>%s &mdash; %s</div>\n",
                                period.getStartDate(), period.getEndDate()));
                        html.append("<div class='period-details'> \n");
                        html.append(String.format("<h5>%s</h5>\n", period.getTitle()));
                        html.append(String.format("<p>%s</p>\n", period.getDescription()));
                        html.append("</div></div>\n");
                    }
                }

                html.append("</div><br/>\n");
                return html.toString();
        }
        return null;
    }

    public static String toEmptyHtml(SectionType type) {
        StringBuilder html = new StringBuilder();

        if (type == SectionType.EDUCATION || type == SectionType.EXPERIENCE) {
            html.append(String.format("<dl><dt>%s</dt><dd>\n", type.getTitle()));

            // Добавить пустую текстовую форму для CompanyName
            html.append("<input type='text' name='CompanyName' size='30' value=''> </br>\n");
            html.append("<input type='text' name='WebSite' size='30' value=''> </br>\n");

            // Добавить маленькие текстовые формы для startDate и endDate на одной строке
            html.append("<input type='text' name='startDate' size='10' value=''>\n");
            html.append("<input type='text' name='endDate' size='10' value=''>\n");

            // Добавить текстовую форму для period.getTitle()
            html.append("<input type='text' name='periodTitle' size='30' value=''>\n");

            // Добавить текстовую форму для period.getDescription()
            html.append("<textarea name='periodDescription' rows='4' cols='40'></textarea>\n");
            html.append("</dd></dl>\n");
            return html.toString();
        }

        // Добавить пустую форму для остальных типов секций
        html.append(String.format("<dl><dt>%s</dt><dd>\n", type.getTitle()));
        html.append(String.format("<textarea name='periodDescription' rows='2' cols='40'></textarea>\n", type.name()));
        html.append("</dd></dl>\n");
        return html.toString();
    }

    public static String toEditableHtml(SectionType type, Section section) {
        StringBuilder html = new StringBuilder();
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection ts = ((TextSection) section);
                String s = ts.getText();
                html.append(String.format("<dl><dt>%s</dt><dd>\n", type.getTitle()));
                html.append(String.format("<textarea name='%s' rows='4' cols='50'>%s</textarea>\n", type.name(), s));
                html.append("</dd></dl>");
                return html.toString();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection ls = ((ListSection) section);
                String[] array = ls.getList().toArray(new String[0]);
                String join = String.join("\n", array);
                html.append(String.format("<dl><dt>%s</dt><dd>\n", type.getTitle()));
                html.append(String.format("<textarea name='%s' rows='10' cols='100'>%s</textarea>\n", type.name(), join));
                html.append("</dd></dl>\n");
                return html.toString();
            case EXPERIENCE:
            case EDUCATION:
                CompanySection cs = ((CompanySection) section);
                List<Company> companies = cs.getCompanies();
                html.append(String.format("<dl><dt>%s</dt><dd></br>\n", type.getTitle()));
                for (Company c : companies) {
                    html.append("<input type='text' name='CompanyName' size='30' value='" + c.getCompanyName() + "'></br>\n");

                    if (c.hasWebsite()) {
                        html.append("<input type='text' name='WebSite' size='30' value='" + c.getWebsite() + "'></br>\n");
                    } else {
                        html.append("<input type='text' name='WebSite' size='40' value=''></br>\n");
                    }

                    for (Company.Period period : c.getPeriods()) {
                        html.append("<input type='text' name='startDate' size='10' value='" + period.getStartDate() + "'>\n");
                        html.append("<input type='text' name='endDate' size='10' value='" + period.getEndDate() + "'></br>\n");

                        // Добавить текстовую форму для period.getTitle()
                        html.append("<input type='text' name='periodTitle' size='30' value='" + period.getTitle() + "'></br>\n");

                        // Добавить текстовую форму для period.getDescription()
                        html.append("<textarea name='periodDescription' rows='5' cols='100'>" + period.getDescription() + "</textarea></br>\n");
                    }
                    html.append("<div style=\"margin-bottom: 30px;\"></div>\n");
                }
                html.append("</dd></dl></br>\n");
                return html.toString();
        }
        return null;
    }

    private static String getHyperLink(String name, String link) {
        return String.format("<a href=\"%s\"> %s </a>", link, name);
    }
}
