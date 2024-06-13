package hr.algebra.threerp3.tictactoe3rp3.controller;

import hr.algebra.threerp3.tictactoe3rp3.CCVRApplication;
import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import hr.algebra.threerp3.tictactoe3rp3.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
@Author(name = "Ana")
public class HelloController implements Initializable {
    public TextField tfPlayerOneName;
    public TextField tfPlayerTwoName;
    public Label lbPlayerOneNameError;
    public Label lbPlayerTwoNameError;
    private final Map<TextField, Label> validationFields = new HashMap<>();
    private static PlayerDetails playerOneDetails;
    private static PlayerDetails playerTwoDetails;

    public void proceed(ActionEvent actionEvent) {
        if (screenValid()) {
            String playerOneName = tfPlayerOneName.getText();
            String playerTwoName = tfPlayerTwoName.getText();

            playerOneDetails = new PlayerDetails(playerOneName);
            playerTwoDetails = new PlayerDetails(playerTwoName);

            System.out.println("Welcome, player 1: " + playerOneName  + " and player 2: "
                    + playerTwoName);

            SceneUtils.setNewSceneToStage("view/game-screen.fxml",
                    "Country City Village River");
        }
    }

    private boolean screenValid() {
        boolean ok = true;

        for (Map.Entry<TextField, Label> entry : validationFields.entrySet()) {
            TextField textField = entry.getKey();
            Label errorLabel = entry.getValue();

            String text = textField.getText().trim();

            ok &= !text.isEmpty();
            errorLabel.setText(text.isEmpty() ? " *Required field" : "");
            /*
            if (text.isEmpty()) {
                errorLabel.setText(" *Required field");
                textField.requestFocus();
            } else {
                errorLabel.setText("");
            }*/
        }

        return ok;
    }

    public static PlayerDetails getPlayerOneDetails() {
        return playerOneDetails;
    }

    public static PlayerDetails getPlayerTwoDetails() {
        return playerTwoDetails;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initValidation();
    }

    private void initValidation() {
        validationFields.put(tfPlayerOneName, lbPlayerOneNameError);
        validationFields.put(tfPlayerTwoName, lbPlayerTwoNameError);
    }
}
