package ru.javawebinar.basejava.util.customfunction;

import java.io.IOException;

@FunctionalInterface
public interface CustomRunnable {
    void run() throws IOException;
}
