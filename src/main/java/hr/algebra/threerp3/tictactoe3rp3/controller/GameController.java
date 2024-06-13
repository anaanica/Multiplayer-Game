package hr.algebra.threerp3.tictactoe3rp3.controller;

import hr.algebra.threerp3.tictactoe3rp3.CCVRApplication;
import hr.algebra.threerp3.tictactoe3rp3.chat.service.RemoteChatService;
import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.*;
import hr.algebra.threerp3.tictactoe3rp3.repository.CityRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.CountryRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.RiverRepository;
import hr.algebra.threerp3.tictactoe3rp3.repository.VillageRepository;
import hr.algebra.threerp3.tictactoe3rp3.threads.Configuration;
import hr.algebra.threerp3.tictactoe3rp3.utils.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_COLS;
import static hr.algebra.threerp3.tictactoe3rp3.model.Constants.NUM_OF_ROWS;

@Author(name = "Ana")
public class GameController {
    public Label lbLetter;
    public Label lbPlayerTwo;
    public TextField tfCountryTwo;
    public TextField tfCityTwo;
    public TextField tfVillageTwo;
    public TextField tfRiverTwo;
    public TextField tfCountryOne;
    public TextField tfCityOne;
    public TextField tfVillageOne;
    public TextField tfRiverOne;
    public Label lbPlayerOne;
    public Button buttonMint;
    public Button buttonLavender;
    public TextArea taChatMessages;
    public TextField tfChatMessage;
    public Label lblLavenderWinRecord;
    public Label lblMintWinRecord;
    public static Set<Country> countries = new TreeSet<>();
    public static Set<City> cities = new TreeSet<>();
    public static Set<Village> villages = new TreeSet<>();
    public static Set<River> rivers = new TreeSet<>();
    public static TextField[][] gridBoard;
    public static Label letter;
    public static String currentLetter;
    public static PlayerDetails playerOneDetails;
    public static PlayerDetails playerTwoDetails;
    public static Integer flag;
    public static CountryRepository countryRepository;
    public static CityRepository cityRepository;
    public static VillageRepository villageRepository;
    public static RiverRepository riverRepository;
    public static Button mint;
    public static Button lavender;
    public static String[] collectiveRow;
    public static RemoteChatService remoteChatService;

    public void initialize() throws Exception {
        if (!UserRole.BLUE.name().equals(CCVRApplication.activeUserRole.name())) {
            tfChatMessage.setOnAction(e -> {
                if (!tfChatMessage.getText().isBlank()) {
                    ChatUtils.sendChatMessage(tfChatMessage.getText());
                    tfChatMessage.clear();
                }
            });
        }

        InitUtils.initRepository();

        playerOneDetails = CCVRApplication.getPlayerOneDetails();
        playerTwoDetails = CCVRApplication.getPlayerTwoDetails();
        playerOneDetails.setPlayerScore(0);
        playerTwoDetails.setPlayerScore(0);
        lbPlayerOne.setText(playerOneDetails.getPlayerName());
        lbPlayerTwo.setText(playerTwoDetails.getPlayerName());

        InitUtils.initSets();

        letter = lbLetter;

        mint = buttonMint;
        lavender = buttonLavender;

        gridBoard = new TextField[NUM_OF_ROWS][NUM_OF_COLS];
        gridBoard[0][0] = tfCountryOne;
        gridBoard[0][1] = tfCityOne;
        gridBoard[0][2] = tfVillageOne;
        gridBoard[0][3] = tfRiverOne;
        gridBoard[1][0] = tfCountryTwo;
        gridBoard[1][1] = tfCityTwo;
        gridBoard[1][2] = tfVillageTwo;
        gridBoard[1][3] = tfRiverTwo;

        collectiveRow = new String[NUM_OF_COLS];

        currentLetter = LetterUtils.getRandomLetter();
        if (!CCVRApplication.isMintAvailable() || !CCVRApplication.isLavenderAvailable()) currentLetter = "L";

        lbLetter.setText(currentLetter);

        flag = 0;

        if (UserRole.LAVENDER.name().equals(CCVRApplication.activeUserRole.name())) {
            enableTextFields(false, playerOneDetails.getPlayerId());
            buttonMint.setDisable(true);
        }
        if (UserRole.MINT.name().equals(CCVRApplication.activeUserRole.name())) {
            enableTextFields(false, playerTwoDetails.getPlayerId());
            buttonLavender.setDisable(true);
            //tfCountryOne.requestFocus();
        }

        if (!UserRole.BLUE.name().equals(CCVRApplication.activeUserRole.name())) {
            if (!CCVRApplication.isMintAvailable() || !CCVRApplication.isLavenderAvailable()) {
                if (UserRole.MINT.name().equals(CCVRApplication.activeUserRole.name())) {
                    RmiUtils.startRmiRemoteChatServer();
                } else {
                    RmiUtils.startRmiRemoteChatClient();
                }
            }


            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(1), e -> ChatUtils.refreshChatMessages(taChatMessages)));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.playFromStart();
        }


        if (UserRole.LAVENDER.name().equals(CCVRApplication.activeUserRole.name())) {
            if (CCVRApplication.isMintAvailable()) {
                GameState gameStateForServer = GameStateUtils.createGameState(gridBoard, currentLetter,
                        playerOneDetails.getPlayerId(), true, flag);
                NetworkingUtils.sendGameStateToServer(gameStateForServer);
            }
        }
        if (UserRole.MINT.name().equals(CCVRApplication.activeUserRole.name())) {
            if (CCVRApplication.isLavenderAvailable()) {
                GameState gameStateForClient = GameStateUtils.createGameState(gridBoard, currentLetter,
                        playerTwoDetails.getPlayerId(), true, flag);
                NetworkingUtils.sendGameStateToClient(gameStateForClient);
            }
        }

        CCVRApplication.mintAvailable = true;
        CCVRApplication.lavenderAvailable = true;

        Timeline refreshingWins = new Timeline(new KeyFrame(
                Duration.seconds(1), e -> refreshLabels()));
        refreshingWins.setCycleCount(Animation.INDEFINITE);
        refreshingWins.playFromStart();
    }

    public void buttonPressed(ActionEvent actionEvent) {
        if (!UserRole.BLUE.name().equals(CCVRApplication.activeUserRole.name())) {
            Button buttonPressed = (Button) actionEvent.getSource();
            buttonPressed.setDisable(true);

            if (buttonPressed.getId().equals("buttonMint")) {
                enableTextFields(false, playerOneDetails.getPlayerId());
            } else if (buttonPressed.getId().equals("buttonLavender")) {
                enableTextFields(false, playerTwoDetails.getPlayerId());
            }
        }

        if (flag == (NUM_OF_ROWS - 1)) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                if (UserRole.LAVENDER.name().equals(CCVRApplication.activeUserRole.name())) {
                    if (collectiveRow[j] != null) {
                        gridBoard[playerOneDetails.getPlayerId()][j].setText(collectiveRow[j]);
                    } else {
                        gridBoard[playerOneDetails.getPlayerId()][j].setText("");
                    }
                }
                if (UserRole.MINT.name().equals(CCVRApplication.activeUserRole.name())) {
                    if (collectiveRow[j] != null) {
                        gridBoard[playerTwoDetails.getPlayerId()][j].setText(collectiveRow[j]);
                    } else {
                        gridBoard[playerTwoDetails.getPlayerId()][j].setText("");
                    }
                }
            }
        }

        if (UserRole.LAVENDER.name().equals(CCVRApplication.activeUserRole.name())) {
            GameState gameStateForServer = GameStateUtils.createGameState(gridBoard, currentLetter,
                    playerTwoDetails.getPlayerId(), false, ++flag);

            NetworkingUtils.sendGameStateToServer(gameStateForServer);
        }
        if (UserRole.MINT.name().equals(CCVRApplication.activeUserRole.name())) {
            GameState gameStateForClient = GameStateUtils.createGameState(gridBoard, currentLetter,
                    playerOneDetails.getPlayerId(), false, ++flag);
            NetworkingUtils.sendGameStateToClient(gameStateForClient);
        }

        if (Objects.equals(flag, NUM_OF_ROWS) ||
                UserRole.BLUE.name().equals(CCVRApplication.activeUserRole.name())) {
            GameResultUtils.calculateResult(gridBoard, currentLetter, countries, cities, villages,
                    rivers);
            playerOneDetails.setPlayerScore(GameResultUtils.playerOneScore);
            playerTwoDetails.setPlayerScore(GameResultUtils.playerTwoScore);
            GameResultUtils.checkWinner(playerOneDetails, playerTwoDetails);
            GameMovesUtils.recordGameMoves(gridBoard, playerOneDetails, playerTwoDetails);
            XMLUtils.saveXML();
        }
    }

    public static void enableTextFields(Boolean enable, Integer row) {
        for (int col = 0; col < NUM_OF_COLS; col++) {
            gridBoard[row][col].setEditable(enable);
        }
    }

    public void sendChatMessage() {
        if (!UserRole.BLUE.name().equals(CCVRApplication.activeUserRole.name())) {
            ChatUtils.sendChatMessage(tfChatMessage.getText());
            tfChatMessage.clear();
        }
    }

    public synchronized void refreshWinRecordLabels() {
        while (Configuration.xmlUsed) {
            try {
                System.out.println("Refresh attempt failed");
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        Configuration.xmlUsed = true;

        lblMintWinRecord.setText(Configuration.mintValue);
        lblLavenderWinRecord.setText(Configuration.lavenderValue);

        Configuration.xmlUsed = false;

        notifyAll();
    }

    public void refreshLabels() {
        lblMintWinRecord.setText(formatWinRecord(playerOneDetails));
        lblLavenderWinRecord.setText(formatWinRecord(playerTwoDetails));
    }

    private String formatWinRecord(PlayerDetails playerDetails) {
        StringBuilder result = new StringBuilder();
        String plural = (playerDetails.getNumberOfWins() == 1) ? "win" : "wins";
        String formattedString = playerDetails.getNumberOfWins() + " " + plural;
        result.append(formattedString).append("\n");
        return result.toString();
    }
}
