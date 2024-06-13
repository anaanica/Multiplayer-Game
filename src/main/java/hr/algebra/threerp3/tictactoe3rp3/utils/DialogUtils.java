package hr.algebra.threerp3.tictactoe3rp3.utils;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

@Author(name = "Ana")
public final class DialogUtils {
    private DialogUtils() {
        throw new RuntimeException();
    }

    public static boolean showConfirmDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private static void showInformationDialog(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void printWinner(PlayerDetails winner, PlayerDetails loser) {
        showInformationDialog("Winner information", "Winner is: "
                + winner.toString() + ", loser is: " + loser.toString());
    }

    public static void printDraw(PlayerDetails playerOneDetails, PlayerDetails playerTwoDetails) {
        showInformationDialog("Draw information", "Draw: " +
                playerOneDetails.toString() + "  " + playerTwoDetails.toString());
    }

    public static void printSaveSuccessfull() {
        showInformationDialog("Save successfull",
                "Game saved successfully");
    }

    public static void printLoadSuccessfull() {
        showInformationDialog("Load successfull",
                "Game loaded successfully");
    }

    public static void printDocumentationSuccess() {
        showInformationDialog("Documentation generated", "Documentation successfully generated");
    }

    public static void printXMLSaveSuccessfull() {
        showInformationDialog("XML save successfull", "XML saved successfully");
    }

    public static void printXMLLoadSuccessfull() {
        showInformationDialog("XML load successfull", "XML loaded successfully");
    }
}
