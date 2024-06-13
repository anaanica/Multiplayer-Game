package hr.algebra.threerp3.tictactoe3rp3.controller;

import hr.algebra.threerp3.tictactoe3rp3.CCVRApplication;
import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.GameState;
import hr.algebra.threerp3.tictactoe3rp3.utils.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.*;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;
import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_ROWS;

@Author(name = "Ana")
public class MenuBarController {
    public void exitApp(ActionEvent actionEvent) {
        if (DialogUtils.showConfirmDialog("App exit", "Exit app?")) {
            Platform.exit();
        }
    }

    public void showNewGameScreen(ActionEvent actionEvent) {
        SceneUtils.setNewSceneToStage("view/game-screen.fxml",
                "country city village river " +
                        CCVRApplication.activeUserRole.name().toLowerCase());
    }

    public void saveGame(ActionEvent actionEvent) {
        FileUtils.saveGameToFile(GameController.gridBoard, GameController.currentLetter);
        DialogUtils.printSaveSuccessfull();
    }

    public void loadGame(ActionEvent actionEvent) {
        GameState recoveredGameState = FileUtils.loadGameFromFile();

        GameController.currentLetter = recoveredGameState.getCurrentLetter();
        GameController.letter.setText(GameController.currentLetter);

        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                if (recoveredGameState.getGameBoardSymbols()[i][j] != null) {
                    GameController.gridBoard[i][j].setText(recoveredGameState.getGameBoardSymbols()[i][j]);
                } else {
                    GameController.gridBoard[i][j].setText("");
                }
            }
        }

        DialogUtils.printLoadSuccessfull();
    }

    public void generateDocumentation(ActionEvent actionEvent) {
        ReflectionUtils.generateCompleteDocumentation();

        DialogUtils.printDocumentationSuccess();

        Runtime r = Runtime.getRuntime();
        try {
            //windows
            //r.exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe " +
                   //"\"C:\\Users\\ana\\Desktop\\TicTacToe-3RP3_-_3rd_version\\TicTacToe-3RP3\\files\\documentation.html\"");
            //mac
            r.exec("open -a Safari file:///Users/ana/Desktop/TicTacToe-3RP3/files/documentation.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showHighScoresScreen(ActionEvent actionEvent) {
        SceneUtils.setNewSceneToStage("view/high-scores.fxml",
                "country city village river high scores");
    }

    public void showGameMovesScreen(ActionEvent actionEvent) {
        SceneUtils.setNewSceneToStage("view/game-moves.fxml",
                "country city village river game moves");
    }

    public void replayGame(ActionEvent actionEvent) {
        XMLUtils.loadXML();
    }
}
