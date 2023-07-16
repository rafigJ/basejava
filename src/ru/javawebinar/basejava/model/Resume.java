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
    private final Map<ContactType, String> contactMap;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid not be Null");
        Objects.requireNonNull(fullName, "fullName not be Null");
        this.uuid = uuid;
        this.fullName = fullName;
        sectionMap = new HashMap<>();
        contactMap = new HashMap<>();
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
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }
        sectionMap.get(type).addDataIntoSection(text);
    }

    private void addSection(SectionType type){
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                sectionMap.put(type, new TextSection(type.getTitle()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sectionMap.put(type, new MarkTextSection(type.getTitle()));
                break;
            case EXPERIENCE:
            case EDUCATION:
                sectionMap.put(type, new DataTextSection(type.getTitle()));
                break;
        }
    }

    public Map<SectionType, Section> getAllSectionInfo(){
        return sectionMap;
    }

    public String getSectionInfo(SectionType type){
        if (sectionMap.containsKey(type)){
            return sectionMap.get(type).getInsideData();
        }
        return null;
    }

    public Map<ContactType, String> getAllContactInfo(){
        return contactMap;
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
