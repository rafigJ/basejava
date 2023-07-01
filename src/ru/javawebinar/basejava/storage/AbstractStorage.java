package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExist(index)) {
            throw new NotExistStorageException(r.getUuid());
        }
        resumeReplace(r, index);
    }

    protected abstract void resumeReplace(Resume r, int index);

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (isExist(index)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(r, index);
        }
    }

    protected abstract void saveResume(Resume r, int index);

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    protected abstract Resume getResume(int index);

    protected abstract int getIndex(String uuid);

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }

    protected abstract void deleteResume(int index);

    protected final boolean isExist(int index) {
        return index > -1;
    }
}
