package ru.javawebinar.basejava.model;

public class TextSection implements Section{
    private String text;

    @Override
    public void addDataIntoSection(String text) {
        this.text = text;
    }

    @Override
    public String getInsideData() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
