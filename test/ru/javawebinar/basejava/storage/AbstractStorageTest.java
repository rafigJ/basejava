package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("./storage");
    protected final Storage storage;

    private static final String UUID_NOT_EXIST = "uuid_not_exist";
    protected static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    public static final String FULL_NAME_1 = "August";
    public static final String FULL_NAME_2 = "Bar";
    public static final String FULL_NAME_3 = "Car";
    protected static final Resume RESUME_1 = ResumeTestData.getFullRandomResume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = ResumeTestData.getFullRandomResume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = ResumeTestData.getFullRandomResume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws ExistStorageException {
        storage.clear();

        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size(){
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertEquals(Collections.emptyList(), storage.getAllSorted());
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume r = storage.get(UUID_1);
        Resume r2 = new Resume(UUID_1, " ");
        assertNotSame(r, r2);

        storage.update(r2);
        r = storage.get(UUID_1);
        assertSame(r, r2);
    }

    @Test
    public void updateNotExist() throws NotExistStorageException{
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @Test
    public void getAllSorted(){
        List<Resume> actual = storage.getAllSorted();
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        assertEquals(actual, expected);
    }

    @Test
    public void save() throws ExistStorageException {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() throws ExistStorageException {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }


    @Test
    public void delete() throws NotExistStorageException {
        assertGet(RESUME_1);
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void deleteNotExist() throws NotExistStorageException {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void get() throws NotExistStorageException {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    private void assertGet(Resume r){
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test
    public void getNotExist() throws NotExistStorageException {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }
}
