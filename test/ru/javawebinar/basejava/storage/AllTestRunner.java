package ru.javawebinar.basejava.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite // TODO: Добавить ещё тест
@SelectClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class,
        MapUuidStorageTest.class, MapResumeStorageTest.class, ObjectStreamStorageTest.class})
public class AllTestRunner {
}
