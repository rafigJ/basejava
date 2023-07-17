package ru.javawebinar.basejava.model;

public interface Section {

    String getInsideData();

    void addDataIntoSection(String text);

    default void addDataIntoSection(CompanySection.Company company){}
}
