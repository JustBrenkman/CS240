package evil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DictionaryLoader {
    static List<String> loadDictionary(Path path) {
        try {
            List<String> words = new ArrayList<>();
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
