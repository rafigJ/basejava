package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.SerializationType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private SerializationType serialization;

    protected FileStorage(File directory, SerializationType serialization) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "serializationType must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serialization = serialization;
    }

    public void setSerialization(SerializationType serialization) {
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        File[] files = getFileList();
        for (File f : files) {
            try {
                deleteResume(f);
            } catch (Exception e) {
                throw new RuntimeException("Can't delete file", e);
            }
        }
    }

    @Override
    public int size() {
        return getFileList().length;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] fileList = getFileList();
        for (File f : fileList) {
            resumes.add(getResume(f));
        }
        return resumes;
    }

    private File[] getFileList(){
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("ERROR: this not a directory or IO exception");
        }
        return files;
    }

    @Override
    protected void replaceResume(Resume r, File file) {
        try {
            serialization.writeResume(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            file.createNewFile();
            serialization.writeResume(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return serialization.readResume(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("can't delete this file", file.getName());
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

}
