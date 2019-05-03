package ch.lukasakermann.connectfourchallenge.connectFourService;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final String winner;
    private final boolean finished;
    private final String currentPlayerId;
    private final List<Player> players;
    private final ArrayList<ArrayList<String>> board;

    @JsonCreator
    public Game(@JsonProperty("winner") String winner,
                @JsonProperty("currentPlayerId") String currentPlayerId,
                @JsonProperty("finished") boolean finished,
                @JsonProperty("players") List<Player> players,
                @JsonProperty("board") ArrayList<ArrayList<String>> board) {

        this.winner = winner;
        this.finished = finished;
        this.currentPlayerId = currentPlayerId;
        this.players = players;
        this.board = board;
    }

    public String getWinner() {
        return winner;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public ArrayList<ArrayList<String>> getBoard() {
        return board;
    }

    public boolean isFinished() {
        return finished;
    }
}
