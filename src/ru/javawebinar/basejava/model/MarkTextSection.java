package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class MarkTextSection implements Section {
    private final String title;
    private final List<String> markText;

    public MarkTextSection(String title) {
        this.title = title;
        this.markText = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getInsideData() {
        return markText.toString();
    }

    public void addDataIntoSection(String text) {
        markText.add(text);
    }

    @Override
    public String toString() {
        return title + " " + markText;
    }
}
