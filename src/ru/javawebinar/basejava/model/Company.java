package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DataUtil.of;
import static ru.javawebinar.basejava.util.DataUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private String companyName;
    private String website;
    private final List<Period> periods = new ArrayList<>();

    public Company() {
    }

    public Company(String companyName) {
        this(companyName, null);
    }

    public Company(String companyName, String url) {
        Objects.requireNonNull(companyName, "companyName must be not null");
        this.companyName = companyName;
        this.website = url == null ? "" : url;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWebsite() {
        return website;
    }

    public boolean hasWebsite() {
        return Objects.equals(this.website, "");
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return companyName + '(' + website + ')' + ':' + periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!Objects.equals(companyName, company.companyName)) return false;
        if (!Objects.equals(website, company.website)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = companyName != null ? companyName.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + periods.hashCode();
        return result;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Period() {
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDateDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description == null ? "" : description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!startDate.equals(period.startDate)) return false;
            if (!endDate.equals(period.endDate)) return false;
            if (!title.equals(period.title)) return false;
            return Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
