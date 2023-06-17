package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExist(index)) {
            System.out.printf("ERROR: a resume with a similar uuid is not present in the storage; uuid: %s%n", r.getUuid());
            return;
        }
        storage[index] = r;
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.printf("ERROR: the storage is overflowing with uuid %s%n", r.getUuid());
        } else if (isExist(getIndex(r.getUuid()))) {
            System.out.printf("ERROR: a resume with a similar uuid is present in the storage; uuid: %s%n", r.getUuid());
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            System.out.printf("ERROR: the resume with the entered uuid is not present in the storage. uuid: %s%n", uuid);
            return;
        }
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
