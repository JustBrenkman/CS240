package com.cs240.spellingcorrector;

import java.io.IOException;
import java.nio.file.Paths;

public class Corrector implements ISpellCorrector {

    private Trie trie;

    Corrector() {
        trie = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        String[] words = DictionaryLoader.loadDictionary(Paths.get(dictionaryFileName));
        for (String word : words) {
            trie.add(word);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        ITrie.INode node = trie.find(inputWord);
        if (node != null)
            return node.toString();
        return null;
    }
}
