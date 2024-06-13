package hr.algebra.threerp3.tictactoe3rp3.controller;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;
import hr.algebra.threerp3.tictactoe3rp3.model.GameMove;
import hr.algebra.threerp3.tictactoe3rp3.model.PlayerDetails;
import hr.algebra.threerp3.tictactoe3rp3.utils.GameMovesUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
@Author(name = "Ana")
public class GameMovesController {
    public TableView<GameMove> tvMoves;
    public TableColumn<GameMove, String> tcPlayer, tcCountry,
            tcCity, tcVillage, tcRiver, tcTime;

    public void initialize() {
        initTableCells();
        ObservableList<GameMove> moves = FXCollections.observableList(GameMovesUtils.lastGameMoves);
        tvMoves.setItems(moves);
    }

    private void initTableCells() {
        tcPlayer.setCellValueFactory(param -> {
            PlayerDetails playerDetails = param.getValue().getPlayerDetails();
            if (playerDetails != null) {
                return new SimpleStringProperty(playerDetails.getPlayerName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        tcCountry.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeoEntities().get(0)));
        tcCity.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeoEntities().get(1)));
        tcVillage.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeoEntities().get(2)));
        tcRiver.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGeoEntities().get(3)));
        tcTime.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getLocalDateTime().toString()));
    }
}
