package evil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String...args) {
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

    private List<String> words;
    private int wordLength;
    private int numGuess;

    private EvilHangman(List<String> words, int wordLength, int numGuess) {
        this.words = words;
        this.wordLength = wordLength;
        this.numGuess = numGuess;
    }

    private boolean doesCharExist(Character c) {
        for (String w : words) {
            if (w.contains(c.toString()))
                return true;
        }
        return false;
    }

    private void filterWords() {

    }

    public void takeGuess() {
        Pattern.Mask mask = new Pattern.Mask(5);
        mask.set(1);
        mask.set(2);

        Pattern.Mask mask1 = new Pattern.Mask(5);
        mask1.set(1);
        mask1.set(2);
        Pattern.Mask mask2 = new Pattern.Mask(5);
        mask2.set(2);
        mask2.set(3);
        Pattern.Mask mask3 = new Pattern.Mask(5);
        mask3.set(3);
        mask3.set(4);

        System.out.println("Mask similarity: " + mask.equals(mask1));
        System.out.println("Mask similarity: " + mask.equals(mask2));
        System.out.println("Mask similarity: " + mask.equals(mask3));

        System.out.println("Mask compatibility: " + mask.isCompatibleWith(mask1));
        System.out.println("Mask compatibility: " + mask.isCompatibleWith(mask2));
        System.out.println("Mask compatibility: " + mask.isCompatibleWith(mask3));
//        List<Character> guesses = new ArrayList<>();
//        Scanner input = new Scanner(System.in);
//        while (guesses.size() < numGuess) {
//            System.out.printf("You have %s guesses left\n", numGuess - guesses.size());
//            System.out.println("Used letters: " + guesses.toString());
//            System.out.println("Word: -----");
//            String character;
//            do {
//                System.out.print("Enter a guess: ");
//                character = input.next();
//                if (guesses.contains(character.charAt(0))) {
//                    System.out.println("You have already used that letter");
//                    continue;
//                }
//                if (doesCharExist(character.charAt(0))) {
//                    guesses.add(character.charAt(0));
//                } else {
//                    System.out.println("Sorry, there are no " + character.charAt(0) + "\'s");
//                }
//            } while (!guesses.contains(character.charAt(0)));
//        }
    }
}
