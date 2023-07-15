package ru.javawebinar.basejava.model;

public interface Section<T> {
    String getTitle();
    T getInsideData();
}
