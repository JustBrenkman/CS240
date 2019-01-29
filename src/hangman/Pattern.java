package hangman;

import java.util.*;

public class Pattern {
    public Mask mask;
    Character type;
    HashMap<Integer, Character> word;

    public Pattern(Mask m, Character c) {
        this.mask = m; this.type = c;
        this.word = new HashMap<>();
        for (int i = 0; i < m.mask.size(); i++) {
            word.put(i, '-');
        }
    }

    @Override
    public String toString() {
        return type + " -> " + mask.toString();
    }

    private void merge(Pattern p) {
        this.mask.mergeWith(p.mask);
    }

    public int getNumberOfMasks() {
        return mask.getNumberOfMasks();
    }

    void applyPattern(Pattern pattern) {
        merge(pattern);
        for (int i = 0; i < pattern.mask.mask.size(); i++) {
            if (pattern.mask.mask.get(i))
                word.put(i, pattern.type);
        }
//        System.out.println("Merged");
    }

    String wordToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < word.size(); i++)
            str.append(word.get(i));
        return str.toString();
    }

    static Pattern generatePattern(String word, Character c) {
        StringBuilder str = new StringBuilder();
        Mask mask = new Mask(word.length());
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c)
                mask.set(i);
        }
        return new Pattern(mask, c);
    }

    public boolean canMerge(Pattern pattern) {
        return this.mask.isCompatibleWith(pattern.mask);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pattern) && (((Pattern) obj).mask.equals(this.mask));
    }

    static class Pair {
        public Pattern pattern;
        public String word;
        Pair(Pattern pattern, String word) {
            this.pattern = pattern;
            this.word = word;
        }
    }

    /**
     * This is the mask class, the mask class holds the mask of the pattern
     * The pattern class is used to hold more information and functions to combine and such
     */
    static class Mask {
        HashMap<Integer, Boolean> mask;
        Mask(HashMap<Integer, Boolean> m) {
//            super();
            this.mask = m;
        }

        Mask(int size) {
            mask = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
                mask.put(i, false);
            }
        }

        boolean at(int index) {
            if (index >= mask.size())
                return false;
            return mask.getOrDefault(index, false); // fancy :)
        }

        boolean get(int index) {
            return mask.getOrDefault(index, false);
        }

        void set(int index) {
            if (index < mask.size())
                mask.put(index, true);
        }

        void remove(int index) {
            if (index < mask.size())
                mask.put(index, false);
        }

        boolean doesContainMask() {
            for (Map.Entry<Integer, Boolean> m : mask.entrySet()) {
                if (m.getValue())
                    return true;
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder string = new StringBuilder();
            string.append("[");
            for (int i = 0; i < mask.size(); i++) {
                string.append(mask.getOrDefault(i, false) ? "*" : "_");
                if (i < mask.size() - 1)
                    string.append(", ");
            }
            string.append("]");
            return string.toString();
        }

        List<Integer> getListOfMasks() {
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < mask.size(); i++) {
                if (mask.get(i))
                    list.add(i);
            }

            list.sort(Integer::compareTo);
            return list;
        }

        boolean isCompatibleWith(Mask mask) {
            if (mask.mask.size() != this.mask.size())
                return false;

            for (Map.Entry<Integer, Boolean> m : this.mask.entrySet()) {
                if (mask.get(m.getKey()) && m.getValue())
                    return false;
            }

            return true;
        }

        protected Mask cloneMask() {
            return new Mask((HashMap<Integer, Boolean>) this.mask.clone());
        }

        int getNumberOfMasks() {
            int count = 0;
            for (Map.Entry<Integer, Boolean> m : mask.entrySet()) {
                if (m.getValue())
                    count++;
            }
            return count;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Mask) {
                Mask other = (Mask) obj;
                if (other.mask.size() != this.mask.size())
                    return false;
                for (Map.Entry<Integer, Boolean> m : this.mask.entrySet()) {
                    if (m.getValue() != other.mask.get(m.getKey()))
                        return false;
                }
            }
            return true;
        }

        public void mergeWith(Mask mask) {
            if (this.isCompatibleWith(mask)) {
                for (int i = 0; i < mask.mask.size(); i++) {
                    if (mask.get(i))
                        set(i);
                }
            }
        }
    }
}
