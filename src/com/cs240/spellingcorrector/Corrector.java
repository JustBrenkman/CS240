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
//        System.out.println("Word count: " + trie.getWordCount());
//        System.out.println("Node count: " + trie.getNodeCount());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        ITrie.INode node = trie.find(inputWord);
        if (node != null)
            return node.toString();
        return null;
    }

    void doTests() throws IOException {
        Trie test1 = new Trie();
        Trie test2 = new Trie();

        String[] words = DictionaryLoader.loadDictionary(Paths.get("res/test1.txt"));
        for (String word1 : words) {
            test1.add(word1);
        }

        words = DictionaryLoader.loadDictionary(Paths.get("res/test2.txt"));
        for (String word : words) {
            test2.add(word);
        }

        System.out.println("Test file 1");
        System.out.println(test1.toString());
        System.out.println();
        System.out.println("Test file 2");
        System.out.println(test2.toString());
        System.out.println(".equals() " + test1.equals(test2));
    }
}
