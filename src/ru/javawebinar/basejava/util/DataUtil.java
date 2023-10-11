package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Company;

import java.time.LocalDate;
import java.time.Month;

public class DataUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String toMonthYear(LocalDate localDate) {
        return localDate.toString().substring(0, 7);
    }

    public static LocalDate parseWithoutDay(String dateWithoutDay) {
        LocalDate date = LocalDate.parse(dateWithoutDay + "-01");
        return date.isBefore(LocalDate.now()) ? date : NOW;
    }

    public static Company.Period periodOf(String start, String end, String title, String description) {
        LocalDate startDate = DataUtil.parseWithoutDay(start);
        LocalDate endDate = DataUtil.parseWithoutDay(end);
        if (!startDate.isBefore(endDate)) {
            throw new IllegalArgumentException("startDate " + startDate + " should always be before endDate " + endDate);
        }
        return new Company.Period(
                startDate,
                endDate,
                title,
                description);
    }
}
