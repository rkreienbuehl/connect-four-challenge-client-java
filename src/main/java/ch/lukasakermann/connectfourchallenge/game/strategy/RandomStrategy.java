package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomStrategy implements ConnectFourStrategy {

    private static final String EMPTY_CELL = "EMPTY";

    @Override
    public int dropDisc(Game game) {
        ArrayList<ArrayList<String>> board = game.getBoard();
        List<String> columns = board.get(0);
        List<Integer> validMoves = IntStream.range(0, columns.size())
                .boxed()
                .filter(column -> columns.get(column).equals(EMPTY_CELL))
                .collect(Collectors.toList());

        Random rand = new Random();
        return validMoves.get(rand.nextInt(validMoves.size()));
    }
}
