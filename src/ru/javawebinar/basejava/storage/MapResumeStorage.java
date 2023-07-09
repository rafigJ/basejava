package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void replaceResume(Resume r, Object searchKey) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        Resume r = (Resume) searchKey;
        storage.remove(r.getUuid());
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
