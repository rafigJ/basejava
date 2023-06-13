package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.printf("ERROR: a resume with a similar uuid is not present in the storage; uuid: %s%n", r.getUuid());
            return;
        }
        storage[index] = r;
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.printf("ERROR: the storage is overflowing with uuid %s%n", r.getUuid());
        } else if (isExist(r.getUuid())) {
            System.out.printf("ERROR: a resume with a similar uuid is present in the storage; uuid: %s%n", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    private boolean isExist(String uuid) {
        return getIndex(uuid) != -1;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.printf("ERROR: A resume with the given uuid is not in storage. uuid: %s%n", uuid);
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.printf("ERROR: the resume with the entered uuid is not present in the storage. uuid: %s%n", uuid);
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
