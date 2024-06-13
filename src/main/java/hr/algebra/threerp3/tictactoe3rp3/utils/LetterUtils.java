package hr.algebra.threerp3.tictactoe3rp3.utils;

import java.util.Random;

public final class LetterUtils {
    private LetterUtils() {
    }

    public static String getRandomLetter() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        int randomIndex = rand.nextInt(alphabet.length());
        return String.valueOf(alphabet.charAt(randomIndex));
    }

}
