package spell;

import java.util.*;

public class Trie implements ITrie {
    private Node root; // Root node
    private int wordCount = 0; // number of unique words in structure
    private int nodeCount = 0; // number of nodes to make structure
    private char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                               'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Trie data structure constructor
     */
    public Trie() {
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
        if (ref.count == 0)
            wordCount++;
        ref.increaseCount();
    }

    /**
     * Adds a single char to the data structure
     *
     * @param c - Char to add
     * @param node - node to add char to
     * @return - returns node
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

    /**
     * Finds a word that is similar to the given word
     *
     * @param word The word being searched for
     * @return The node that the word is at, just call toString() on node to print word
     */
    @Override
    public INode find(String word) {
        INode ref = findWholeWord(word);
        if (ref != null) {
            return ref;
        } else {
            // This is were the magic happens
            // First check for an edit distance of 1
            // Checking deletion distance
            List<Node> possibles = new ArrayList<>();
            for (int i = 0; i < word.length(); i++) {
                StringBuilder stringBuilder = new StringBuilder(word);
                stringBuilder.deleteCharAt(i);
                Node node = (Node) findWholeWord(stringBuilder.toString());
                if (node != null)
                    possibles.add(node);
            }
            for (int i = 0; i < word.length() - 1; i++) {
                StringBuilder stringBuilder = new StringBuilder(word);
                Character c = stringBuilder.charAt(i);
                stringBuilder.deleteCharAt(i);
                stringBuilder.insert(i + 1, c);
                Node node = (Node) findWholeWord(stringBuilder.toString());
                if (node != null)
                    possibles.add(node);
            }
            for (int i = 0; i < word.length(); i++) {
                for (int j = 0; j < alphabet.length; j++) {
                    StringBuilder stringBuilder = new StringBuilder(word);
                    stringBuilder.deleteCharAt(i);
                    stringBuilder.insert(i, alphabet[j]);
                    Node node = (Node) findWholeWord(stringBuilder.toString());
                    if (node != null)
                        possibles.add(node);
                }
            }
            for (int i = 0; i < word.length() + 1; i++) {
                for (int j = 0; j < alphabet.length; j++) {
                    StringBuilder stringBuilder = new StringBuilder(word);
                    stringBuilder.insert(i, alphabet[j]);
                    Node node = (Node) findWholeWord(stringBuilder.toString());
                    if (node != null)
                        possibles.add(node);
                }
            }

            if (possibles.size() == 0) {
                // do the second distance edit
                for (int i = 0; i < word.length(); i++) {
                    StringBuilder stringBuilder = new StringBuilder(word);
                    stringBuilder.deleteCharAt(i);
                    for (int j = 0; j < stringBuilder.length(); j++) {
                        StringBuilder second = new StringBuilder(stringBuilder);
                        second.deleteCharAt(j);
                        int m = 0; // Forget this, just to get rid of stuff
                        Node node = (Node) findWholeWord(second.toString());
                        if (node != null)
                            possibles.add(node);
                    }
                }
                for (int i = 0; i < word.length() - 1; i++) {
                    StringBuilder stringBuilder = new StringBuilder(word);
                    Character c = stringBuilder.charAt(i);
                    stringBuilder.deleteCharAt(i);
                    stringBuilder.insert(i + 1, c);
                    for (int j = 0; j < stringBuilder.length() - 1; j++) {
                        StringBuilder second = new StringBuilder(stringBuilder);
                        Character d = second.charAt(j);
                        second.deleteCharAt(j);
                        second.insert(j + 1, d);
                        Node node = (Node) findWholeWord(second.toString());
                        if (node != null)
                            possibles.add(node);
                    }
                }
                for (int i = 0; i < word.length(); i++) {
                    for (char c : alphabet) {
                        StringBuilder stringBuilder = new StringBuilder(word);
                        stringBuilder.deleteCharAt(i);
                        stringBuilder.insert(i, c);
                        for (int k = 0; k < stringBuilder.length(); k++) {
                            for (char c1 : alphabet) {
                                StringBuilder second = new StringBuilder(stringBuilder);
                                second.deleteCharAt(k);
                                second.insert(k, c1);
                                Node node = (Node) findWholeWord(second.toString());
                                if (node != null)
                                    possibles.add(node);
                            }
                        }
                    }
                }
                for (int i = 0; i < word.length() + 1; i++) {
                    for (char c : alphabet) {
                        StringBuilder stringBuilder = new StringBuilder(word);
                        stringBuilder.insert(i, c);
                        for (int j = 0; j < stringBuilder.length() + 1; j++) {
                            for (char c1 : alphabet) {
                                StringBuilder second = new StringBuilder(stringBuilder);
                                second.insert(j, c1);
                                Node node = (Node) findWholeWord(second.toString());
                                if (node != null)
                                    possibles.add(node);
                            }
                        }
                    }
                }
            }

            if (possibles.size() > 0) {
                possibles.sort(this::compare);
                int highVal = possibles.get(0).count;
                possibles.removeIf(p -> p.count < highVal);
                if (possibles.size() > 1) {
                    possibles.sort(this::compareAlph);
                }
                return possibles.get(0);
            } else {
                return null;
            }
        }
    }

    private int compare(Node x, Node y) {
        return Integer.compare(y.count, x.count);
    }

    private int compareAlph(Node x, Node y) {
        return y.c.compareTo(x.c);
    }

    private INode findWholeWord(String word) {
//        System.out.println("Checking word: " + word);
        Node ref = root;
        for (int i = 0; i < word.length(); i++) {
            if (ref.children.containsKey(word.charAt(i))) {
                ref = ref.children.get(word.charAt(i));
            } else {
                return null;
            }
        }
        if (ref.isWord()) {
            return ref;
        } else
            return null;
    }

    /**
     * Gets number of words in structure
     *
     * @return numnber of unique words
     */
    @Override
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Gets number of nodes required in structure
     *
     * @return number of nodes
     */
    @Override
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Hashcode for structure
     *
     * @return hashcode for tries
     */
    @Override
    public int hashCode() {
        return wordCount + nodeCount;
    }

    /**
     * Determines if two trie objects contain the same data structure and words
     *
     * @param obj Object to compare
     * @return equal to or not
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
            if (!deep(ref.getValue(), ref2))
                return false;
        }
        return true;
    }

    /**
     * Prints all words in data structure
     *
     * @return List of words in alphabetical order
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
     * @param builder list of words
     * @param node node to dive to
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
