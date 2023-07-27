package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage{
    public FileStorage(File directory) {
        super(directory);
    }

    private SerializationType serializationType;

    public void setSerializationType(SerializationType serializationType) {
        this.serializationType = serializationType;
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        serializationType.writeResume(r, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializationType.readResume(is);
    }
}
