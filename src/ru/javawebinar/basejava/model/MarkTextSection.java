package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class MarkTextSection implements Section<List<String>>{
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
    public List<String> getInsideData() {
        return markText;
    }

    public void addText(String text){
        markText.add(text);
    }
}
