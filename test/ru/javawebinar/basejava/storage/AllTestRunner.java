package ru.javawebinar.basejava.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class,
        MapUuidStorageTest.class, MapResumeStorageTest.class, ObjectStreamStorageTest.class,
        ObjectStreamPathStorageTest.class, XmlPathStorageTest.class, DataPathStorageTest.class,
        JsonPathStorageTest.class
})
public class AllTestRunner {
}
