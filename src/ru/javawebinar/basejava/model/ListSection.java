package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection implements Section {
    private final List<String> list;

    public ListSection() {
        this.list = new ArrayList<>();
    }

    @Override
    public void addDataIntoSection(String text) {
        list.add(text);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
