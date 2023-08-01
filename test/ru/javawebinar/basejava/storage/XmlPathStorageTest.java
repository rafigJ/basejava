package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.XmlStreamSerialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class XmlPathStorageTest extends AbstractStorageTest {
    protected XmlPathStorageTest(){
        super(new PathStorage(STORAGE_DIR.toPath().toString(), new XmlStreamSerialization()));
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume r = storage.get(UUID_1);
        Resume r2 = new Resume(UUID_1, " ");
        assertNotSame(r, r2);

        storage.update(r2);
        r = storage.get(UUID_1);
        assertEquals(r, r2);
    }
}