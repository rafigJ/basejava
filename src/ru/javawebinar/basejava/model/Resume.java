package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid not be Null");
        Objects.requireNonNull(fullName, "fullName not be Null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContactInfo(ContactType type, String text){
        contactMap.put(type, text);
    }

    public void addInfoAtSection(SectionType type, String text){
        if (isCompanySectionType(type)){
            throw new RuntimeException(String.format("%s is Company section type but expected Text/List-Section type", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }

        switch (type){
            case PERSONAL:
            case OBJECTIVE:
                TextSection textSection = (TextSection) sectionMap.get(type);
                textSection.setText(text);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection listSection = (ListSection) sectionMap.get(type);
                listSection.getList().add(text);
                break;
        }
    }

    public void addInfoAtSection(SectionType type, Company company){
        if(!isCompanySectionType(type)){
            throw new RuntimeException(String.format("%s is NOT Company section type ", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }
        CompanySection companySection = (CompanySection) sectionMap.get(type);
        companySection.getCompanies().add(company);
    }

    private boolean isCompanySectionType(SectionType type){
        return type == SectionType.EXPERIENCE || type == SectionType.EDUCATION;
    }

    private void addSection(SectionType type){
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                sectionMap.put(type, new TextSection());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sectionMap.put(type, new ListSection());
                break;
            case EXPERIENCE:
            case EDUCATION:
                sectionMap.put(type, new CompanySection());
                break;
        }
    }

    public Section getSection(SectionType type){
        return sectionMap.get(type);
    }

    public String getContactInfo(ContactType type){
        return contactMap.get(type);
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
