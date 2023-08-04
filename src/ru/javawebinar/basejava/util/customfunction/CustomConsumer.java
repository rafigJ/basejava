package ru.javawebinar.basejava.util.customfunction;

import java.io.IOException;

@FunctionalInterface
public interface CustomConsumer<T> {
    void accept(T a) throws IOException;
}
