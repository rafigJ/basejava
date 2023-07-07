package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    private static final int STORAGE_LIMIT = AbstractArrayStorage.STORAGE_LIMIT;

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveNotOverflow() throws StorageException {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume(Integer.toString(i)));
            } catch (StorageException e) {
                fail("Storage overflowed before time");
            }
        }
    }

    @Test
    public void saveOverflow() throws StorageException {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume(Integer.toString(i)));
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

}