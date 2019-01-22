package evil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void takeGuess() {
        List<Character> guesses = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while (guesses.size() < numGuess) {
            System.out.printf("You have %s guesses left\n", numGuess - guesses.size());
            System.out.println("Used letters: " + guesses.toString());
            System.out.println("Word: -----");
            String character;
            do {
                System.out.print("Enter a guess: ");
                character = input.next();
                guesses.add(character.charAt(0));
                if (guesses.contains(character.charAt(0)))
                    System.out.println("You have already used that letter");
            } while (guesses.contains(character));
        }
    }
}
