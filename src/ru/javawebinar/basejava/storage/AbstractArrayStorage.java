package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage
        implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public final void saveResume(Resume r, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow ", r.getUuid());
        } else {
            saveArrayStorage(r, index);
            size++;
        }
    }

    protected abstract void saveArrayStorage(Resume r, int index);

    @Override
    protected final Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected final void deleteResume(int index) {
        deleteArrayStorage(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteArrayStorage(int index);

    @Override
    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public final int size() {
        return size;
    }

}
