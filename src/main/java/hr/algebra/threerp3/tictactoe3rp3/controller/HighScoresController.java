package hr.algebra.threerp3.tictactoe3rp3.controller;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.utils.GameResultUtils;
import javafx.scene.control.TextArea;

import java.util.Map;

@Author(name = "Ana")
public class HighScoresController {
    public TextArea taHighScores;

    public void initialize() {
        taHighScores.setText(getScores());
    }

    private String getScores() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Integer> entry : GameResultUtils.allScores.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            String plural = (value == 1) ? "win" : "wins";
            String formattedString = key + ": " + value + " " + plural;
            result.append(formattedString).append("\n");
        }

        return result.toString();
    }
}
