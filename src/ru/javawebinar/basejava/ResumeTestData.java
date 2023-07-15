package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.HashMap;
import java.util.Map;


public class ResumeTestData {

    public static void main(String[] args) {
        Map<SectionType, Section> map = new HashMap<>();
        map.put(SectionType.PERSONAL, new TextSection(SectionType.PERSONAL.getTitle()));
        map.put(SectionType.ACHIEVEMENT, new MarkTextSection(SectionType.ACHIEVEMENT.getTitle()));
        map.put(SectionType.EDUCATION, new DataTextSection(SectionType.EDUCATION.getTitle()));
        ((DataTextSection) map.get(SectionType.EDUCATION))
                .addDataText("10/2003", "11/2029", "Header", "Text field");

        for (Map.Entry<SectionType, Section> m: map.entrySet()) {
            System.out.printf("%s = %s\n", m.getKey().getTitle(), m.getValue().getInsideData());
        }
    }
}
