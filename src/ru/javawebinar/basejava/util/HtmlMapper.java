package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

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
                return "<a href='mailto:" + value + "'>" + value + "</a>";
            default:
                return ct.getTitle() + ": " + value;
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
                            .append("</li>");
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
                    html.append("</h4>\n </br> <table>\n");
                    for (Company.Period period : c.getPeriods()) {
                        html.append(String.format("<tr>\n <th> %s </br> %s </th>", period.getStartDate(), period.getEndDate()));
                        html.append(String.format("<th>" +
                                "%s <p>\n" +
                                "%s\n </p></th>\n</tr>", period.getTitle(), period.getDescription()));
                    }
                    html.append("</table>\n");
                }
                html.append("</div><br/>\n");
                return html.toString();
        }
        return null;
    }

    private static String getHyperLink(String name, String link) {
        return String.format("<a href=\"%s\"> %s </a>", link, name);
    }
}
