package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage implements Storage {
    protected final List<Resume> storage = new LinkedList<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected final void replaceResume(Resume r, Object searchKey) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected final void saveResume(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected final Resume getResume(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected final Object getSearchKey(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    protected final void deleteResume(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey > -1;
    }

    @Override
    public final Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
