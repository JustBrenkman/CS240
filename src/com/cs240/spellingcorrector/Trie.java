package com.cs240.spellingcorrector;

import java.util.*;

public class Trie implements ITrie {
    private Node root; // Root node
    private int wordCount = 0; // number of words in structure
    private int nodeCount = 0; // number of nodes to make structure

    /**
     * Trie data structure constructor
     */
    Trie() {
        root = new Node(null, null);
        nodeCount++;
    }

    /**
     * Adds a word to the trie data structure
     *
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
     *
     * @param c
     * @param node
     * @return
     */
    private Node addChar(Character c, Node node) {
        Node ret;
        if (!node.children.containsKey(c)) {
            ret = new Node(c, node);
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
     *
     * @param word The word being searched for
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
     *
     * @return
     */
    @Override
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Gets number of nodes required in structure
     *
     * @return
     */
    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Hashcode for structure
     *
     * @return
     */
    @Override
    public int hashCode() {
        return wordCount + nodeCount;
    }

    /**
     * Determines if two trie objects contain the same data structure and words
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trie) {
            if (((Trie) obj).nodeCount != this.nodeCount || ((Trie) obj).wordCount != this.wordCount)
                return false;
            return deep(root, ((Trie) obj).root);
        } else {
            return false;
        }
    }

    /**
     * Recursively check the whole tree
     * @param node1 - tree
     * @param node2 - tree to compare
     * @return - if it has the same values or not
     */
    private boolean deep(Node node1, Node node2) {
        for (Map.Entry<Character, Node> ref : node1.children.entrySet()) {
            if (!node2.children.containsKey(ref.getKey()))
                return false;
            Node ref2 = node2.children.get(ref.getKey());
            if (ref2.count != ref.getValue().count)
                return false;
            deep(ref.getValue(), ref2);
        }
        return true;
    }

    /**
     * Clones the data structure
     *
     * @return new Trie object
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Prints all words in data structure
     *
     * @return
     */
    @Override
    public String toString() {
        List<String> builder = new ArrayList<>();
//        for (Map.Entry<Character, Node> ref : root.children.entrySet()) {
//            dive(builder, ref.getValue());
//        }
        dive(builder, root);
        Collections.sort(builder);
        StringBuilder stringify = new StringBuilder();
        for (String word : builder)
            stringify.append(word).append('\n');

        return stringify.toString();
    }

    /**
     * Fun little recursive action going on here
     *
     * @param builder
     * @param node
     */
    private void dive(List<String> builder, Node node) {
        for (Map.Entry<Character, Node> ref : node.children.entrySet()) {
            if (ref.getValue().isWord())
                builder.add(ref.getValue().toString());
            dive(builder, ref.getValue());
        }
    }

    /**
     * This is the Node class, basic building block of the trie data structure
     */
    public class Node implements INode {

        private HashMap<Character, Node> children;
        private int count;
        private Node parent;
        private Character c;

        private void increaseCount() {
            count++;
        }

        Node(Character c, Node parent) {
            children = new HashMap<>();
            count = 0;
            this.parent = parent;
            this.c = c;
        }

        boolean isWord() {
            return this.count > 0;
        }

        Node getParent() {
            return parent;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            Node ref = this;
            while (ref.c != null) {
                builder.append(ref.c);
                ref = ref.getParent();
            }
            return builder.reverse().toString();
        }

        @Override
        public int getValue() {
            return count;
        }
    }
}
