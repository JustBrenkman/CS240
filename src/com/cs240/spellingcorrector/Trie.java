package com.cs240.spellingcorrector;

import java.util.HashMap;

public class Trie implements ITrie {
    private Node root;

    Trie() {
        root = new Node();
    }

    @Override
    public void add(String word) {
        Node ref = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);
            ref = addChar(c, ref);
        }
        ref.increaseCount();
    }

    private Node addChar(Character c, Node node) {
        Node ret;
        if (!node.children.containsKey(c)) {
            ret = new Node();
            node.children.put(c, ret);
        } else {
            ret = node.children.get(c);
        }
        return ret; // returns node where the last char was inserted
    }

    boolean doTest() {
        return root.children.get('p').children.get('r').children.get('o').children.get('j').children.get('e').children.get('c').children.containsKey('t');
    }

    @Override
    public INode find(String word) {
        return null;
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }

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

        @Override
        public int getValue() {
            return count;
        }
    }
}
