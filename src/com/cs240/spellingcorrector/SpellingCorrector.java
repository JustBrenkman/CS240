package com.cs240.spellingcorrector;

import java.io.IOException;

public class SpellingCorrector {

    /**
     * Give the dictionary file name as the first argument and the word to correct
     * as the second argument.
     */
    public static void main(String[] args) throws IOException {

        String dictionaryFileName = args[0];
        String inputWord = args[1];

        /**
         * Create an instance of your corrector here
         */
        ISpellCorrector corrector = null;

        corrector.useDictionary(dictionaryFileName);
        String suggestion = corrector.suggestSimilarWord(inputWord);
        if (suggestion == null) {
            suggestion = "No similar word found";
        }

        System.out.println("Suggestion is: " + suggestion);
    }
}
