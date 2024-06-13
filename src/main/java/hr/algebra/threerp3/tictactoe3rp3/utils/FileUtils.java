package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.GameState;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Properties;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;
import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_ROWS;

@Author(name = "Ana")
public final class FileUtils {
    public static final String SAVE_GAME_FILE_NAME = "saveGame.dat";
    public static final String DOCUMENTATION_FILE_NAME = "files/documentation.html";
    public static final String XML_FILE_NAME = "moves.xml";
    public static final String XML_RECORD_FILE_NAME = "record.xml";

    private FileUtils() {
    }

    public static void saveGameToFile(TextField[][] gridBoard, String letter)
    {
        String[][] gameBoardStrings = new String[NUM_OF_ROWS][NUM_OF_COLS];

        for(int i = 0; i < NUM_OF_ROWS; i++) {
            for(int j = 0; j < NUM_OF_COLS; j++) {
                if(!gridBoard[i][j].getText().isBlank()) {
                    gameBoardStrings[i][j] = gridBoard[i][j].getText();
                }
            }
        }

        GameState gameStateToSave = new GameState(gameBoardStrings, letter);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FileUtils.SAVE_GAME_FILE_NAME))) {
            oos.writeObject(gameStateToSave);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameState loadGameFromFile() {
        GameState recoveredGameState;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FileUtils.SAVE_GAME_FILE_NAME))) {
            recoveredGameState = (GameState) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return recoveredGameState;
    }

}
