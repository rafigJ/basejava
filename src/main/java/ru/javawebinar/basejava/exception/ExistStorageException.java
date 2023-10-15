package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR: a resume with a similar uuid is present in the storage; uuid: " + uuid, uuid);
    }
}
