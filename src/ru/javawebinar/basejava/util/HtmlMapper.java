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
                html.append("</ul>\n</div><br/>\n");
                return html.toString();
            case EXPERIENCE:
            case EDUCATION:
                CompanySection cs = ((CompanySection) section);
                html.append(String.format("<div><h2>%s</h2></div>\n<div><ul>\n", st.getTitle()));

                for (Company c : cs.getCompanies()) {
                    html.append("<li><h3>\n");
                    if (c.hasWebsite()) {
                        html.append(getHyperLink(c.getCompanyName(), c.getWebsite()));
                    } else {
                        html.append(c.getCompanyName());
                    }
                    html.append("</h3></li>\n");

                    html.append("<table style=\"border-spacing: 10px\";>\n");
                    for (Company.Period period : c.getPeriods()) {
                        html.append("<tr>\n");

                        html.append(String.format("<td align='center' style=\"width: 100px;\">%s </br> &mdash; </br> %s</td>\n",
                                period.getStartDate(), period.getEndDate()));

                        html.append("<td>\n");
                        html.append(String.format("<h3>%s</h3>\n", period.getTitle()));
                        html.append(String.format("<p>%s</p>\n", period.getDescription()));
                        html.append("</td>\n");

                        html.append("</tr>\n");
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
