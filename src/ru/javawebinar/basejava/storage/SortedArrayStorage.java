package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size == STORAGE_LIMIT) {
            System.out.printf("ERROR: the storage is overflowing with uuid %s%n", r.getUuid());
        } else if (isExist(index)) {
            System.out.printf("ERROR: a resume with a similar uuid is present in the storage; uuid: %s%n", r.getUuid());
        } else {
            index = Math.abs(index + 1);
            for (int i = size; i > index; i--) {
                storage[i] = storage[i - 1];
            }
            storage[index] = r;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            System.out.printf("ERROR: the resume with the entered uuid is not present in the storage. uuid: %s%n", uuid);
            return;
        }
        for (int i = index; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size - 1] = null; // для случая если size == STORAGE_LIMIT - 1
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
