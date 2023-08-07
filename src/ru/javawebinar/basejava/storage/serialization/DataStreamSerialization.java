package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.customfunction.CustomConsumer;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerialization implements SerializationType {

    @Override
    public void write(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContactMap();

            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSectionMap();
            writeWithException(sections.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(entry.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        writeWithException(list, dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCompanySection((CompanySection) entry.getValue(), dos);
                        break;
                }
            });
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContactInfo(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addInfoAtSection(type, dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String[] strings = new String[dis.readInt()];
                        for (int j = 0; j < strings.length; j++) {
                            strings[j] = dis.readUTF();
                        }
                        resume.addInfoAtSection(type, strings);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addInfoAtSection(type, readCompanySection(dis));
                        break;
                }
            }
            return resume;
        }
    }

    private static void writeCompanySection(CompanySection companySection, DataOutputStream dos) throws IOException {
        List<Company> companies = companySection.getCompanies();
        writeWithException(companies, dos, c -> {
            dos.writeUTF(c.getCompanyName());
            String website = c.getWebsite();
            if (website != null) {
                dos.writeBoolean(true);
                dos.writeUTF(website);
            } else {
                dos.writeBoolean(false);
            }

            List<Company.Period> periods = c.getPeriods();
            writeWithException(periods, dos, p -> {
                dos.writeUTF(p.getStartDate().toString());
                dos.writeUTF(p.getEndDate().toString());
                dos.writeUTF(p.getTitle());
                dos.writeUTF(p.getDescription());
            });
        });
    }

    private static Company[] readCompanySection(DataInputStream dis) throws IOException {
        Company[] companies = new Company[dis.readInt()];
        for (int i = 0; i < companies.length; i++) {
            String name = dis.readUTF();
            if (dis.readBoolean()) {
                String website = dis.readUTF();
                companies[i] = new Company(name, website);
            } else {
                companies[i] = new Company(name);
            }
            List<Company.Period> periods = companies[i].getPeriods();
            int periodSize = dis.readInt();
            for (int j = 0; j < periodSize; j++) {
                LocalDate startDate = LocalDate.parse(dis.readUTF());
                LocalDate endDate = LocalDate.parse(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                periods.add(new Company.Period(startDate, endDate, title, description));
            }
        }
        return companies;
    }

    private static <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<? super T> action) throws IOException {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(action);
        Objects.requireNonNull(dos);
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }
}