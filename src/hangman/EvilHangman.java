package hangman;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class EvilHangman implements IEvilHangmanGame {

    public static void main(String... args) throws GuessAlreadyMadeException {
        if (args.length == 3) {
            EvilHangman evilHangman = new EvilHangman(
                    DictionaryLoader.loadDictionary(Paths.get(args[0])),
                    Integer.valueOf(args[1]),
                    Integer.valueOf(args[2])
            );
            evilHangman.takeGuess();
        } else {
            System.out.println("Usage:  java [your main class name] dictionary wordLength guesses");
        }
    }

    private Set<Pattern.Pair> words;
    private int wordLength;
    private int numGuess;
    Pattern word;
    List<Character> guesses;


    public EvilHangman(Set<String> words, int wordLength, int numGuess) {
        this.wordLength = wordLength;
        this.numGuess = numGuess;
        this.words = new HashSet<>();
        guesses = new ArrayList<>();

        word = new Pattern(new Pattern.Mask(wordLength), ' ');

        for (String str : words)
            this.words.add(new Pattern.Pair(null, str));
    }

    private boolean doesCharExist(Character c) {
        for (Pattern.Pair w : words) {
            if (w.word.contains(c.toString()))
                return true;
        }
        return false;
    }

    private Pattern filterWords(Character c, int wordLength) {
        Set<Set<Pattern.Pair>> filtered = new HashSet<>();
        for (Pattern.Pair str : words) {
            if (str.word.length() == wordLength) {
                Pattern pattern = Pattern.generatePattern(str.word, c);
                if (filtered.isEmpty()) {
                    filtered.add(new HashSet<>());
                    for (Set<Pattern.Pair> s : filtered) {
                        s.add(new Pattern.Pair(pattern, str.word));
                        break;
                    }
                    continue;
                }

                boolean mustAddNewRow = true;
                for (Set<Pattern.Pair> p : filtered) {
                    for (Pattern.Pair g : p) {
                        if (g.pattern.equals(pattern)) {
                            p.add(new Pattern.Pair(pattern, str.word));
                            mustAddNewRow = false;
                            break;
                        }
                        break;
                    }
                }
                if (mustAddNewRow) {
                    Set<Pattern.Pair> row = new HashSet<>();
                    row.add(new Pattern.Pair(pattern, str.word));
                    filtered.add(row);
                }
            }
        }
        if (!filtered.isEmpty()) {
            int maxSize = 0;
            for (Set<Pattern.Pair> p : filtered) {
                if (p.size() > maxSize)
                    maxSize = p.size();
            }
            int finalMaxSize = maxSize;
            filtered.removeIf(g -> g.size() < finalMaxSize);

            for (Set<Pattern.Pair> p : filtered) {
                this.words = p;
                for (Pattern.Pair g : p) {
                    return g.pattern;
                }
            }
        }
//        System.out.println("Done");
        return null;
    }

    private void replaceWords(Set<Pattern.Pair> filtered) {
        this.words = filtered;
    }

    int compare(List<Pattern.Pair> groupA, List<Pattern.Pair> groupB) {
        return Integer.compare(groupB.size(), groupA.size());
    }

    public void takeGuess() {
        Scanner input = new Scanner(System.in);
        while (guesses.size() < numGuess) {
//            System.out.printf("There are %s many words", this.words.size());
            System.out.printf("You have %s guesses left\n", numGuess - guesses.size());
            StringBuilder stringBuilder = new StringBuilder(guesses.toString());
            stringBuilder.deleteCharAt(0).deleteCharAt(stringBuilder.length() - 1);
            System.out.println("Used letters: " + stringBuilder.toString());
            System.out.println("Word: " + word.wordToString());
            String character;
            Character c;
            boolean runAgain = true;
            do {
                System.out.print("Enter a guess: ");
                character = input.next();
                c = character.charAt(0);
                if (guesses.contains(c)) {
                    System.out.println("You have already used that letter");
                    continue;
                }
                try {
                    Set<String> filt = makeGuess(c);
                    if (filt.size() == 1) {
                        for (String f : filt) {
                            if (f.equals(word.wordToString()))
                                System.out.println("You win!");
                                return;
                        }
                    }
                    runAgain = false;
                } catch (GuessAlreadyMadeException e) {
                    runAgain = true;
                }
            } while (runAgain);
            System.out.println();
        }
        System.out.println("You lose!");
        for (Pattern.Pair w : words) {
            System.out.println("The word was: " + w.word);
            break;
        }
    }

    @Override
    public void startGame(File dictionary, int wordLength) {
        EvilHangman evilHangman = new EvilHangman(
                DictionaryLoader.loadDictionary(dictionary.toPath()), wordLength, 1000
        );
    }

    Set<String> wordsToList() {
        Set<String> list = new HashSet<>();
        for (Pattern.Pair g : words) {
            list.add(g.word);
        }
        return list;
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        if (guesses.contains(guess))
            throw new GuessAlreadyMadeException();
        guesses.add(guess);
        Pattern patternToApply = filterWords(guess, wordLength);
        if (patternToApply != null) {
            word.applyPattern(patternToApply);
            if (!patternToApply.mask.doesContainMask()) {
                System.out.printf("Sorry there are no %s\'s\n", guess);
                return wordsToList();
            } else {
                System.out.printf("Yes there is %d %s\n", patternToApply.getNumberOfMasks(), patternToApply.type);
                return wordsToList();
            }
        }
        System.out.printf("Sorry there are no %s\'s\n", guess);
        return wordsToList();
    }
}
