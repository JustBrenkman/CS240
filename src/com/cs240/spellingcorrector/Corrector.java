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
        for (int i = 0; i < words.length; i++) {
            trie.add(words[i]);
        }

//        System.out.println(trie.doTest());
        System.out.println("Word count: " + trie.getWordCount());
        System.out.println("Node count: " + trie.getNodeCount());
//        System.out.println();
//        System.out.println("Doing test, word = hello");
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return "Hello";
    }
}
