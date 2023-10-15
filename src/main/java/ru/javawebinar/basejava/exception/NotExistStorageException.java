package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("ERROR: a resume with a similar uuid is not present in the storage; uuid: " + uuid, uuid);
    }
}
