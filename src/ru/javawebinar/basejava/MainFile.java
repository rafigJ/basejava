package ru.javawebinar.basejava;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String pathname = ".";
        File file = new File(pathname);
        System.out.println(file.getCanonicalPath());
//        File dir = new File("./src/ru/javawebinar/basejava");
//        System.out.println(dir.getCanonicalPath());
//        for (String s : Objects.requireNonNull(dir.list())) {
//            System.out.println(s);
//        }
//        try(FileInputStream fileInputStream = new FileInputStream(pathname)) {
//            System.out.println(fileInputStream.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        Queue<File> queue = new LinkedList<>();
        queue.add(file);
        while (!queue.isEmpty()) {
            for (File f : Objects.requireNonNull(queue.poll().listFiles())) {
                if (f.isDirectory()) {
                    queue.add(f);
                } else {
                    System.out.println(f.getName());
                }
            }
        }

    }
}
