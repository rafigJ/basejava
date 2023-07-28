package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.SerializationType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationType serialization;

    protected PathStorage(String dir, SerializationType serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serialization, "serializationType must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serialization = serialization;
    }

    public void setSerialization(SerializationType serialization) {
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        Stream<Path> pathStream = getPathStream();
        pathStream.forEach(this::deleteResume);
        pathStream.close();
    }

    @Override
    public int size() {
        Stream<Path> pathStream = getPathStream();
        int count = (int) pathStream.count();
        pathStream.close();
        return count;
    }

    @Override
    protected List<Resume> doCopyAll() {
        Stream<Path> pathStream = getPathStream();
        List<Resume> list = pathStream.map(this::getResume).collect(Collectors.toList());
        pathStream.close();
        return list;
    }

    private Stream<Path> getPathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("ERROR: this not a directory or IO exception");
        }
    }


    @Override
    protected void replaceResume(Resume r, Path path) {
        try {
            serialization.writeResume(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected void saveResume(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
        replaceResume(r, path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return serialization.readResume(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("can't delete this path file", path.getFileName().toString());
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

}
