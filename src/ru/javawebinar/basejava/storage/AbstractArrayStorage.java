package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            System.out.printf("ERROR: A resume with the given uuid is not in storage. uuid: %s%n", uuid);
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected boolean isExist(int index) {
        return index != -1;
    }
}
