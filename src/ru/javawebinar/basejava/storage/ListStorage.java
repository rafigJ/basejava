package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage
        implements Storage {
    protected final List<Resume> storage = new LinkedList<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    protected final void doUpdate(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    protected final void saveResume(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected final Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected final int getIndex(String uuid) {
        Resume r = new Resume(uuid);
        return storage.indexOf(r);
    }

    @Override
    protected final void deleteResume(int index) {
        storage.remove(index);
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
