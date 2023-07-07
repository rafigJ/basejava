package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private final Map<Resume, Resume> storage = new HashMap<>();

    @Override
    protected void replaceResume(Resume r, Object searchKey) {
        storage.replace((Resume) searchKey, r);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        storage.put((Resume) searchKey, r);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return storage.get((Resume) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        storage.remove((Resume) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((Resume) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> tempList = new ArrayList<>(storage.values());
        tempList.sort(resumeComparator());
        return tempList;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
