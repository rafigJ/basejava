package ru.javawebinar.basejava.model;

import java.time.LocalDate;
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

    public static class Company {
        private final String companyName;
        private final List<Period> periods = new ArrayList<>();

        public Company(String companyName) {
            this.companyName = companyName;
        }

        public void addPeriodAtList(LocalDate startDate, LocalDate endDate, String title, String description) {
            periods.add(new Period(startDate, endDate, title, description));
        }

        @Override
        public String toString() {
            return companyName + ':' + periods;
        }
    }
}
