package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName);

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

    public final List<Resume> getAllSorted(){
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract void replaceResume(Resume r, Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void deleteResume(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

}
