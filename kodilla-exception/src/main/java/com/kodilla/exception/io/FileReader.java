package com.kodilla.exception.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
    public void readFile() throws FileReaderException{

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("file/name.txt").getFile());

        try (Stream<String> fileLine = Files.lines(Paths.get("file/txt.txt"))){
            fileLine.forEach(System.out::println);
        } catch (IOException e) {
            throw new FileReaderException();
        } finally {
            System.out.println("I`m gonna be here... ALWAYS!!!");
        }

    }
}