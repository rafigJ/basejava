package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;


public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final int STORAGE_LIMIT = 10000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws ExistStorageException {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume r = storage.get(UUID_1);
        Resume r2 = new Resume(UUID_1);
        assertNotSame(r, r2);

        storage.update(r2);
        r = storage.get(UUID_1);
        assertSame(r, r2);
    }

    @Test
    public void updateNotExist() throws NotExistStorageException{
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("uuid4")));
    }

    @Test
    public void getAll(){
        Resume[] res = storage.getAll();
        assertEquals(res[0], storage.get(UUID_1));
        assertEquals(res[1], storage.get(UUID_2));
        assertEquals(res[2], storage.get(UUID_3));
    }

    @Test
    public void save() throws ExistStorageException {
        Resume r = new Resume("uuid4");
        storage.save(r);
        assertEquals(r, storage.get("uuid4"));
    }

    @Test
    public void saveExist() throws ExistStorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    public void saveNotOverflow() throws StorageException {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume(Integer.toString(i)));
            }
            catch (StorageException e){
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

    @Test
    public void delete() throws NotExistStorageException {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        storage.delete(UUID_1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void deleteNotExist() throws NotExistStorageException {
        assertThrows(NotExistStorageException.class, () -> storage.delete("-1"));
    }

    @Test
    public void get() throws NotExistStorageException {
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        assertEquals(new Resume(UUID_2), storage.get(UUID_2));
        assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test
    public void getNotExist() throws NotExistStorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}