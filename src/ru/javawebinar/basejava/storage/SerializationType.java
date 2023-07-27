package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationType {
    void writeResume(Resume resume, OutputStream os);

    Resume readResume(InputStream is);
}
