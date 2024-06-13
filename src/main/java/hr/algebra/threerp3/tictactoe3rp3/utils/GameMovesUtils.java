package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.model.GameMove;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;

public final class GameMovesUtils {
    private static List<String> geoEntitiesPlayerOne = new ArrayList<>();
    private static List<String> geoEntitiesPlayerTwo = new ArrayList<>();
    public static List<GameMove> lastGameMoves = new ArrayList<>();
    private GameMovesUtils() {
    }

    public static void recordGameMoves(TextField[][] gridBoard, PlayerDetails playerOneDetails,
                                       PlayerDetails playerTwoDetails) {
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i = 0; i < NUM_OF_COLS; i++) {
            geoEntitiesPlayerOne.add(gridBoard[0][i].getText());
        }
        for (int i = 0; i < NUM_OF_COLS; i++) {
            geoEntitiesPlayerTwo.add(gridBoard[1][i].getText());
        }
        List<String> copy = new ArrayList<>(geoEntitiesPlayerOne);
        recordGameMove(playerOneDetails, copy, localDateTime);
        geoEntitiesPlayerOne.clear();
        List<String> copy2 = new ArrayList<>(geoEntitiesPlayerTwo);
        recordGameMove(playerTwoDetails, copy2, localDateTime);
        geoEntitiesPlayerTwo.clear();
    }

    private static void recordGameMove(PlayerDetails playerDetails, List<String> geoEntities,
                                       LocalDateTime localDateTime) {
        GameMove newGameMove = new GameMove(
                playerDetails,
                geoEntities,
                localDateTime);
        lastGameMoves.add(newGameMove);
    }
}
