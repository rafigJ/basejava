package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage implements Storage {
    protected final List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        if(!storage.contains(r))
            throw new NotExistStorageException(r.getUuid());
        storage.set(storage.indexOf(r), r);
    }

    @Override
    public void save(Resume r) {
        if(storage.contains(r)){
            throw new ExistStorageException(r.getUuid());
        }
        else {
            storage.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if(index < 0){
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    protected int getIndex(String uuid){
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            if (storage.get(i).getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
