package hr.algebra.threerp3.tictactoe3rp3.model;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Author(name = "Ana")
public class GameMove {
    private PlayerDetails playerDetails;
    private List<String> geoEntities;
    private LocalDateTime localDateTime;

    public GameMove(PlayerDetails playerDetails, List<String> geoEntities, LocalDateTime localDateTime) {
        this.playerDetails = playerDetails;
        this.geoEntities = geoEntities;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "GameMove{" +
                "Player name: " + playerDetails.getPlayerName() +
                ", Country: " + geoEntities.get(0) + ", City: " + geoEntities.get(1) +
                ", Village: " + geoEntities.get(2) + ", River: " + geoEntities.get(3) +
                ", localDateTime=" + localDateTime +
                '}';
    }

    public PlayerDetails getPlayerDetails() {
        return playerDetails;
    }

    public void setPlayerDetails(PlayerDetails playerDetails) {
        this.playerDetails = playerDetails;
    }

    public List<String> getGeoEntities() {
        return geoEntities;
    }

    public void setGeoEntities(List<String> geoEntities) {
        this.geoEntities = geoEntities;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
