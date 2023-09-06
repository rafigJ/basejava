package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);

    public Resume() {
    }

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

    public Map<SectionType, Section> getSectionMap() {
        return sectionMap;
    }

    public Map<ContactType, String> getContactMap() {
        return contactMap;
    }

    public void addContactInfo(ContactType type, String text) {
        contactMap.put(type, text);
    }

    public void addInfoAtSection(SectionType type, String text) {
        if (isCompanySectionType(type)) {
            throw new RuntimeException(String.format("%s is Company section type but expected Text/List-Section type", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }

        switch (type) {
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

    public void addInfoAtSection(SectionType type, String... texts) {
        if (isCompanySectionType(type) || isTextSectionType(type)) {
            throw new RuntimeException(String.format("%s is Company/Text section type but expected ListSection type", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }
        ListSection listSection = (ListSection) sectionMap.get(type);
        listSection.getList().addAll(Arrays.asList(texts));
    }

    public void addInfoAtSection(SectionType type, Company company) {
        if (!isCompanySectionType(type)) {
            throw new RuntimeException(String.format("%s is NOT Company section type ", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }
        CompanySection companySection = (CompanySection) sectionMap.get(type);
        companySection.getCompanies().add(company);
    }

    public void addInfoAtSection(SectionType type, Company... company) {
        if (!isCompanySectionType(type)) {
            throw new RuntimeException(String.format("%s is NOT Company section type ", type));
        }
        if (!sectionMap.containsKey(type)) {
            addSection(type);
        }
        CompanySection companySection = (CompanySection) sectionMap.get(type);
        companySection.getCompanies().addAll(Arrays.asList(company));
    }

    private boolean isCompanySectionType(SectionType type) {
        return type == SectionType.EXPERIENCE || type == SectionType.EDUCATION;
    }

    private boolean isTextSectionType(SectionType type) {
        return type == SectionType.PERSONAL || type == SectionType.OBJECTIVE;
    }

    private void addSection(SectionType type) {
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

    public Section getSection(SectionType type) {
        return sectionMap.get(type);
    }

    public String getContactInfo(ContactType type) {
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
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
//                Objects.equals(sectionMap, resume.sectionMap) &&
                Objects.equals(contactMap, resume.contactMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sectionMap, contactMap);
    }
}
