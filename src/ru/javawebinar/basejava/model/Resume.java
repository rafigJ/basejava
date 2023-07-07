package ru.javawebinar.basejava.model;

import java.util.Random;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private String fullName;

    private static final Random R = new Random();
    private static final String[] RANDOM_NAMES = {"Пахомов Корнелий", "Орлов Григорий", "Филатов Аристарх",
            "Горшков Степан", "Соболев Демьян", "Коновалов Аким", "Поляков Борис", "Белозёров Вольдемар",
            "Мельников Геннадий", "Мельников Эдуард"};

    public Resume() {
        this(UUID.randomUUID().toString(), RANDOM_NAMES[R.nextInt(10)]);
    }

    public Resume(String uuid){
        this(uuid, RANDOM_NAMES[R.nextInt(10)]);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
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
