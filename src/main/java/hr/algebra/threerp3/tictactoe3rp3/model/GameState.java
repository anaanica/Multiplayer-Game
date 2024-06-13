package hr.algebra.threerp3.tictactoe3rp3.model;

import hr.algebra.threerp3.tictactoe3rp3.info.Author;

import java.io.Serializable;

@Author(name = "Ana")
public class GameState implements Serializable {
    private String[][] gameBoardSymbols;
    private String currentLetter;
    private Integer playerId;
    private Boolean newGame;
    private Integer flag;

    public GameState(String[][] gameBoardSymbols, String currentLetter) {
        this.gameBoardSymbols = gameBoardSymbols;
        this.currentLetter = currentLetter;
    }

    public GameState(String[][] gameBoardSymbols, String currentLetter, Integer playerId, Boolean newGame,
                     Integer flag) {
        this.gameBoardSymbols = gameBoardSymbols;
        this.currentLetter = currentLetter;
        this.playerId = playerId;
        this.newGame = newGame;
        this.flag = flag;
    }

    public String[][] getGameBoardSymbols() {
        return gameBoardSymbols;
    }

    public void setGameBoardSymbols(String[][] gameBoardSymbols) {
        this.gameBoardSymbols = gameBoardSymbols;
    }

    public String getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(String currentLetter) {
        this.currentLetter = currentLetter;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Boolean isNewGame() {
        return newGame;
    }

    public void setNewGame(Boolean newGame) {
        this.newGame = newGame;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
