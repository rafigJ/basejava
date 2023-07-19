package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection implements Section {
    private final List<Company> companyList;

    public CompanySection() {
        this.companyList = new ArrayList<>();
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    @Override
    public void addDataIntoSection(Company company) {
        companyList.add(company);
    }

    @Override
    public void addDataIntoSection(String text) {}

    @Override
    public String toString() {
        return companyList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companyList.equals(that.companyList);
    }

    @Override
    public int hashCode() {
        return companyList.hashCode();
    }
}
