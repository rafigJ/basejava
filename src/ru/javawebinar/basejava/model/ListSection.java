package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Section {
    private final List<String> list;

    public ListSection() {
        this.list = new ArrayList<>();
    }

    @Override
    public String getInsideData() {
        return list.toString();
    }

    @Override
    public void addDataIntoSection(String text) {
        list.add(text);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
