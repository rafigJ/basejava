package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends Section {
    private static final long serialVersionUID = 1L;

    private final List<Company> companies = new ArrayList<>();

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public String toString() {
        return companies.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
