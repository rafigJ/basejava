package ru.javawebinar.basejava.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String message, String uuid, Throwable cause) {
        super(message, cause);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid) {
        this(message, uuid, null);
    }

    public StorageException(String message, Throwable cause){
        this(message, null, cause);
    }

    public StorageException(String message) {
        this(message, null, null);
    }

    public String getUuid() {
        return uuid;
    }
}
