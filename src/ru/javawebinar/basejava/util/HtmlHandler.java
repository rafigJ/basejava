package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;

import java.util.Map;

public class HtmlHandler {
    public static String toHtml(Map.Entry<ContactType, String> contactEntry) {
        return toHtml(contactEntry.getKey(), contactEntry.getValue());
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
}
