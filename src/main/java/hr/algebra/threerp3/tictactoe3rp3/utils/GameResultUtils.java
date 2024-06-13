package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.*;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;

@Author(name = "Ana")
public final class GameResultUtils {
    public static Integer playerOneScore;
    public static Integer playerTwoScore;
    public static Map<String, Integer> allScores = new HashMap<>();

    private GameResultUtils() {
    }

    public static void calculateResult(TextField[][] gridBoard, String currentLetter,
                                       Set<Country> countries, Set<City> cities, Set<Village> villages,
                                       Set<River> rivers) {
        playerOneScore = 0;
        playerTwoScore = 0;
        String[] categories = {"Country", "City", "Village", "River"};

        for (int col = 0; col < NUM_OF_COLS; col++) {
            Integer doubleScoreValue = gridBoard[0][col].getText().equalsIgnoreCase(gridBoard[1][col].getText()) ? 1 : 2;

            if (!gridBoard[0][col].getText().isBlank()) {
                String word = gridBoard[0][col].getText().toLowerCase();
                String firstLetterAsString = String.valueOf(word.charAt(0));
                if (firstLetterAsString.equals(currentLetter.toLowerCase())) {
                    String category = categories[col];
                    if (category.equals("Country")) {
                        Country country = new Country(word);
                        if (countries.add(country)) {
                            playerOneScore += 0;
                            countries.remove(country);
                        } else {
                            playerOneScore += doubleScoreValue * country.getScoreValue();
                        }
                    } else if (category.equals("City")) {
                        City city = new City(word);
                        if (cities.add(city)) {
                            playerOneScore += 0;
                            cities.remove(city);
                        } else {
                            playerOneScore += doubleScoreValue * city.getScoreValue();
                        }
                    } else if (category.equals("Village")) {
                        Village village = new Village(word);
                        if (villages.add(village)) {
                            playerOneScore += 0;
                            villages.remove(village);
                        } else {
                           playerOneScore += doubleScoreValue * village.getScoreValue();
                        }
                    } else if (category.equals("River")) {
                        River river = new River(word);
                        if (rivers.add(river)) {
                            playerOneScore += 0;
                            rivers.remove(river);
                        } else {
                            playerOneScore += doubleScoreValue * river.getScoreValue();
                        }
                    }
                }
            }

            if (!gridBoard[1][col].getText().isBlank()) {
                String word = gridBoard[1][col].getText().toLowerCase();
                String firstLetterAsString = String.valueOf(word.charAt(0));
                if (firstLetterAsString.equals(currentLetter.toLowerCase())) {
                    String category = categories[col];
                    if (category.equals("Country")) {
                        Country country = new Country(word);
                        if (countries.add(country)) {
                            playerTwoScore += 0;
                            countries.remove(country);
                        } else {
                            playerTwoScore += doubleScoreValue * country.getScoreValue();
                        }
                    } else if (category.equals("City")) {
                        City city = new City(word);
                        if (cities.add(city)) {
                            playerTwoScore += 0;
                            cities.remove(city);
                        } else {
                            playerTwoScore += doubleScoreValue * city.getScoreValue();
                        }
                    } else if (category.equals("Village")) {
                        Village village = new Village(word);
                        if (villages.add(village)) {
                            playerTwoScore += 0;
                            villages.remove(village);
                        } else {
                            playerTwoScore += doubleScoreValue * village.getScoreValue();
                        }
                    } else if (category.equals("River")) {
                        River river = new River(word);
                        if (rivers.add(river)) {
                            playerTwoScore += 0;
                            rivers.remove(river);
                        } else {
                            playerTwoScore += doubleScoreValue * river.getScoreValue();
                        }
                    }
                }
            }
        }
    }

    public static void checkWinner(PlayerDetails playerOneDetails, PlayerDetails playerTwoDetails) {
        if (playerOneDetails.getPlayerScore() == playerTwoDetails.getPlayerScore())
            DialogUtils.printDraw(playerOneDetails, playerTwoDetails);
        else if (playerOneDetails.getPlayerScore() > playerTwoDetails.getPlayerScore()) {
            DialogUtils.printWinner( playerOneDetails, playerTwoDetails);
            recordWin(playerOneDetails);
        } else {
            DialogUtils.printWinner(playerTwoDetails, playerOneDetails);
            recordWin(playerTwoDetails);
        }
    }

    public static void recordWin(PlayerDetails playerDetails) {
        playerDetails.recordWin();
        allScores.put(playerDetails.getPlayerName(), playerDetails.getNumberOfWins());
    }

}
