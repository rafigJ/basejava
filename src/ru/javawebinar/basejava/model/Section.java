package ru.javawebinar.basejava.model;

public interface Section {

    void addDataIntoSection(String text);

    default void addDataIntoSection(CompanySection.Company company){}
}
