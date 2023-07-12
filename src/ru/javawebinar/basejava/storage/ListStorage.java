package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> implements Storage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected final void replaceResume(Resume r, Integer searchKey) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected final void saveResume(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected final Resume getResume(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected final Integer getSearchKey(String uuid) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    protected final void deleteResume(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey > -1;
    }

    @Override
    public final List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
