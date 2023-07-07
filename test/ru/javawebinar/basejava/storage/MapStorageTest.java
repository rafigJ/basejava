package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void getAll() {
        Resume[] actual = storage.getAll();
        Arrays.sort(actual, Comparator.comparing(Resume::getUuid));
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assertions.assertArrayEquals(actual, expected);
    }
}