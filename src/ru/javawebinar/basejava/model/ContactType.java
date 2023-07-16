package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE_NUMBER("Номер телефона"),
    EMAIL("email"),
    LINKEDIN("Linkedin"),
    SKYPE("Skype"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverFlow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
