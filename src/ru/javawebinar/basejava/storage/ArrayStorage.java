package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int indexForUpdate = getIndexResumeByUuid(r.getUuid());
        if (indexForUpdate == -1) {
            System.out.printf("ERROR: a resume with a similar uuid is not present in the storage; uuid: %s%n", r.getUuid());
            return;
        }
        storage[indexForUpdate] = r;
    }

    public void save(Resume r) {
        if (resumeIsPresent(r)) {
            System.out.printf("ERROR: a resume with a similar uuid is present in the storage; uuid: %s%n", r.getUuid());
            return;
        }
        if (size >= storage.length) {
            System.out.printf("ERROR: the storage is overflowing with uuid %s%n", r.getUuid());
        }
        storage[size] = r;
        size++;
    }

    private boolean resumeIsPresent(Resume r) {
        return getIndexResumeByUuid(r.getUuid()) != -1;
    }

    private int getIndexResumeByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Resume get(String uuid) {
        int index = getIndexResumeByUuid(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int indexForRemove = getIndexResumeByUuid(uuid);
        if (indexForRemove == -1) {
            System.out.printf("ERROR: the resume with the entered uuid is not present in the storage; uuid: %s%n", uuid);
            return;
        }
        for (int i = indexForRemove; i < size; i++) {
            storage[i] = storage[i + 1];
        }
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
