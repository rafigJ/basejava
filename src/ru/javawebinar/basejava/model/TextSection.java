package ru.javawebinar.basejava.model;

public class TextSection implements Section{
    private String text;

    @Override
    public void addDataIntoSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection section = (TextSection) o;

        return text.equals(section.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
