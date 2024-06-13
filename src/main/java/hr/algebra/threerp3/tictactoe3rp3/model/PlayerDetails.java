package hr.algebra.threerp3.tictactoe3rp3.model;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;

import java.io.Serializable;

@Author(name = "Ana")
public final class PlayerDetails implements Serializable {
    private static Integer nextId = 0;

    private final Integer playerId;
    private final String playerName;
    private Integer numberOfWins;
    private Integer playerScore;

    public PlayerDetails(String playerName) {
        this.playerName = playerName;
        this.playerId = nextId++;
        numberOfWins = 0;
        playerScore = 0;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getNumberOfWins() {
        return numberOfWins;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(Integer playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String toString() { return playerName + '(' + playerScore + ')'; }

    public void recordWin() {
        ++numberOfWins;
    }
}
