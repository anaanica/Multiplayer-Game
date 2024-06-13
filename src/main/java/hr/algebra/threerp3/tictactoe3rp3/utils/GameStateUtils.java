package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.controller.GameController;
import hr.algebra.threerp3.tictactoe3rp3.model.GameState;
import javafx.scene.control.TextField;

import java.util.Objects;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;
import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_ROWS;

public final class GameStateUtils {
    private GameStateUtils() {
    }

    public static GameState createGameState(TextField[][] gridBoard, String currentLetter,
                                            Integer playerId, Boolean newGame, Integer flag)
    {
        String[][] gameBoardStrings = new String[NUM_OF_ROWS][NUM_OF_COLS];

        for(int i = 0; i < NUM_OF_ROWS; i++) {
            for(int j = 0; j < NUM_OF_COLS; j++) {
                if(!gridBoard[i][j].getText().isBlank()) {
                    gameBoardStrings[i][j] = gridBoard[i][j].getText();
                }
            }
        }

        return new GameState(gameBoardStrings, currentLetter, playerId, newGame, flag);
    }

    public static void loadGameState(GameState gameState) {
        if (gameState.isNewGame()) {
            GameController.collectiveRow = new String[NUM_OF_COLS];

            GameController.currentLetter = gameState.getCurrentLetter();
            GameController.letter.setText(GameController.currentLetter);

            GameController.flag = gameState.getFlag();

            GameController.playerOneDetails.setPlayerScore(0);
            GameController.playerTwoDetails.setPlayerScore(0);

            if (Objects.equals(gameState.getPlayerId(), GameController.playerOneDetails.getPlayerId())) {
                GameController.mint.setDisable(false);
                GameController.enableTextFields(true, GameController.playerOneDetails.getPlayerId());
            } else {
                GameController.lavender.setDisable(false);
                GameController.enableTextFields(true, GameController.playerTwoDetails.getPlayerId());
            }

            for (int i = 0; i < NUM_OF_ROWS; i++) {
                for (int j = 0; j < NUM_OF_COLS; j++) {
                    GameController.gridBoard[i][j].setText("");
                }
            }
        } else {
            if (Objects.equals(gameState.getFlag(), NUM_OF_ROWS)) {
                for (int j = 0; j < NUM_OF_COLS; j++) {
                    if (gameState.getGameBoardSymbols()[gameState.getPlayerId()][j] != null) {
                        GameController.gridBoard[gameState.getPlayerId()][j]
                                .setText(gameState.getGameBoardSymbols()[gameState.getPlayerId()][j]);
                    } else {
                        GameController.gridBoard[gameState.getPlayerId()][j].setText("");
                    }
                }
                GameResultUtils.calculateResult(
                        GameController.gridBoard,
                        GameController.currentLetter,
                        GameController.countries,
                        GameController.cities,
                        GameController.villages,
                        GameController.rivers);
                GameController.playerOneDetails.setPlayerScore(GameResultUtils.playerOneScore);
                GameController.playerTwoDetails.setPlayerScore(GameResultUtils.playerTwoScore);
                GameResultUtils.checkWinner(GameController.playerOneDetails, GameController.playerTwoDetails);
                GameMovesUtils.recordGameMoves(
                        GameController.gridBoard,
                        GameController.playerOneDetails,
                        GameController.playerTwoDetails);
            } else {
                GameController.flag = gameState.getFlag();
                for(int j = 0; j < NUM_OF_COLS; j++) {
                    if (gameState.getGameBoardSymbols()[gameState.getPlayerId()][j] != null) {
                        GameController.collectiveRow[j]
                                = gameState.getGameBoardSymbols()[gameState.getPlayerId()][j];
                    }
                }
            }
        }
    }
}
