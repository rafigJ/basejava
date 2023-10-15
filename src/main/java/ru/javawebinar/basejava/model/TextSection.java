package ru.javawebinar.basejava.model;

public class TextSection extends Section {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
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
