package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, Section> sectionMap;

    public Resume() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid not be Null");
        Objects.requireNonNull(fullName, "fullName not be Null");
        this.uuid = uuid;
        this.fullName = fullName;
        sectionMap = new HashMap<>();
        initializeSectionMap();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void initializeSectionMap() {
        for (SectionType type : SectionType.values()) {
            if (type == SectionType.PERSONAL || type == SectionType.OBJECTIVE) {
                sectionMap.put(type, new TextSection(type.getTitle()));
            } else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
                sectionMap.put(type, new MarkTextSection(type.getTitle()));
            } else {
                sectionMap.put(type, new DataTextSection(type.getTitle()));
            }
        }
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
