package com.cs240.spellingcorrector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

class DictionaryLoader {
    static String[] loadDictionary(Path pathToDictionary) throws IOException {
        try {
            InputStream inputStream = Files.newInputStream(pathToDictionary);
            Scanner scanner = new Scanner(inputStream);

            ArrayList<String> list = new ArrayList<>();

            while (scanner.hasNext()) {
                list.add(scanner.next().toLowerCase());
            }

            return list.toArray(new String[0]);
        } catch (IOException e) {
            throw e;
        }
    }
}
