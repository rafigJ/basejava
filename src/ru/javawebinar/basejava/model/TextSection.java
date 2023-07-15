package ru.javawebinar.basejava.model;

public class TextSection implements Section<String>{
    private final String title;
    private String text;

    public TextSection(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getInsideData() {
        return text;
    }
}
