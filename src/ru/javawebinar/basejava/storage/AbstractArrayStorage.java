package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExist(index)) {
            System.out.printf("ERROR: a resume with a similar uuid is not present in the storage; uuid: %s%n", r.getUuid());
            return;
        }
        storage[index] = r;
    }

    public final int size() {
        return size;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            System.out.printf("ERROR: A resume with the given uuid is not in storage. uuid: %s%n", uuid);
            return null;
        }
        return storage[index];
    }

    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected boolean isExist(int index) {
        return index > -1;
    }
}