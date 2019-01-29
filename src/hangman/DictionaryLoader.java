package hangman;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class DictionaryLoader {
    static Set<String> loadDictionary(Path path) {
        try {
            Set<String> words = new HashSet<>();
            InputStream is = Files.newInputStream(path);
            Scanner scanner = new Scanner(is);

            while (scanner.hasNext())
                words.add(scanner.next().toLowerCase());

            return words;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
