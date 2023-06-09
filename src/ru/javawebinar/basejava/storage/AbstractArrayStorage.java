package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void replaceResume(Resume r, Object searchKey) {
        storage[(int) searchKey] = r;
    }

    @Override
    public final void saveResume(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow ", r.getUuid());
        } else {
            saveArrayStorage(r, (int) searchKey);
            size++;
        }
    }

    @Override
    protected final Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected final void deleteResume(Object searchKey) {
        deleteArrayStorage((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public final List<Resume> doCopyAll() {
        Resume[] tempStorage = new Resume[size];
        System.arraycopy(storage, 0, tempStorage, 0, size);
        return new ArrayList<>(Arrays.asList(tempStorage));
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey > -1;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveArrayStorage(Resume r, int index);

    protected abstract void deleteArrayStorage(int index);

}
