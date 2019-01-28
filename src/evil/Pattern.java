package evil;

import java.util.*;

public class Pattern {
    Pattern(Mask m) {
        this.mask = m;
    }
    Pattern() {}

    private Mask mask;

    @Override
    public String toString() {
        return mask.toString();
    }

    Pattern merge(Pattern pattern) {
        Pattern result;
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Pattern) && (((Pattern) obj).mask.equals(this.mask));
    }

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

        public boolean isCompatibleWith(Mask mask) {
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
    }
}
