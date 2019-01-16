package com.cs240.spellingcorrector;

import java.io.IOException;
import java.nio.file.Paths;

public class Corrector implements ISpellCorrector {

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        String[] words = DictionaryLoader.loadDictionary(Paths.get(dictionaryFileName));

    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return new String("Hello");
    }
}
