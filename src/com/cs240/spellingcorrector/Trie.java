package com.cs240.spellingcorrector;

import java.util.HashMap;

public class Trie implements ITrie {
    private Node root;
    private int wordCount = 0;
    private int nodeCount = 0;

    Trie() {
        root = new Node();
        nodeCount++;
    }

    /**
     * Adds a word to the trie data structure
     * @param word The word being added to the trie
     */
    @Override
    public void add(String word) {
        Node ref = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);
            ref = addChar(c, ref);
        }
        wordCount++;
        ref.increaseCount();
    }

    /**
     * Adds a single char to the data structure
     * @param c
     * @param node
     * @return
     */
    private Node addChar(Character c, Node node) {
        Node ret;
        if (!node.children.containsKey(c)) {
            ret = new Node();
            node.children.put(c, ret);
            nodeCount++;
        } else {
            ret = node.children.get(c);
        }
        return ret; // returns node where the last char was inserted
    }

    boolean doTest() {
        System.out.println(root.children.get('p').children.get('r').children.get('o').children.get('j').children.get('e').children.get('c').count);
        return root.children.get('p').children.get('r').children.get('o').children.get('j').children.get('e').children.get('c').children.containsKey('t');
    }

    /**
     * Finds a word that is similar to the given word
     * @param word The word being searched for
     *
     * @return
     */
    @Override
    public INode find(String word) {
        Node ref = root;
        for (int i = 0; i < word.length(); i++) {
            if (ref.children.containsKey(word.charAt(i))) {
                ref = ref.children.get(word.charAt(i));
            }
        }
        if (ref.isWord()) {
            return ref;
        } else {
            return null;
        }
    }

    /**
     * Gets number of words in structure
     * @return
     */
    @Override
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Gets number of nodes required in structure
     * @return
     */
    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Hashcode for structure
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Determines if two trie objects contain the same data structure and words
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Clones the data structure
     * @return new Trie object
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Prints all words in data structure
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * This is the Node class, basic building block of the trie data structure
     */
    public class Node implements INode {

        private HashMap<Character, Node> children;
        private int count;

        private void increaseCount() {
            count++;
        }

        Node() {
            children = new HashMap<>();
            count = 0;
        }

        boolean isWord() { return this.count > 0; }

        @Override
        public int getValue() {
            return count;
        }
    }
}
