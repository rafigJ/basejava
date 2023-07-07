package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        replaceResume(r, searchKey);
    }

    @Override
    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Comparator<Resume> resumeComparator() {
        return (r1, r2) -> {
            int nameCompared = r1.getFullName().compareTo(r2.getFullName());
            int uuidCompared = r1.getUuid().compareTo(r2.getUuid());
            return nameCompared != 0 ? nameCompared : uuidCompared;
        };
    }

    protected abstract void replaceResume(Resume r, Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void deleteResume(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

}
