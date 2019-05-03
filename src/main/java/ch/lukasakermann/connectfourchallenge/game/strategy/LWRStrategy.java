package ch.lukasakermann.connectfourchallenge.game.strategy;

import ch.lukasakermann.connectfourchallenge.connectFourService.Game;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LWRStrategy implements ConnectFourStrategy {


    private static final String EMPTY_CELL = "EMPTY";
    private final int MAX_DEPTH = 5;
    private final int MAX_STONES = 21;

    @Override
    public int dropDisc(Game game) {

        System.out.println("first");
        System.out.println(game.isFinished());

        int[] maxim = max(game, MAX_DEPTH, 0, 0);

        System.out.println(maxim[0]);

        return maxim[0];
    }


    private String myColor(Game game) {
        return game.getPlayers().stream()
                .filter(player -> player.getPlayerId().equals(game.getCurrentPlayerId()))
                .findFirst()
                .get()
                .getDisc();
    }

    private String otherColor(Game game) {
        return game.getPlayers().stream()
                .filter(player -> !player.getPlayerId().equals(game.getCurrentPlayerId()))
                .findFirst()
                .get()
                .getDisc();
    }


    /**
     * @param game
     * @param depth
     * @param alpha
     * @param beta
     */
    private int[] max(Game game, int depth, int alpha, int beta) {

        System.out.println("in max");



        if (game.isFinished()) {


            int[] zero = {};
            return zero;
        }

        int[] maximum = {-1, -99999};

        System.out.println(maximum);

        for (int i = 0; i < game.getBoard().get(0).size(); i++) {
            System.out.println(i);

            ArrayList<ArrayList<String>> newBoard = (ArrayList<ArrayList<String>>) game.getBoard().clone();
            // System.out.println("new List");
            // Collections.copy(newBoard, game.getBoard());
            // System.out.println("copy");


            Game intern = new Game(game.getWinner(), game.getCurrentPlayerId(), game.isFinished(), game.getPlayers(), newBoard);

            if (checkAndSetPosition(intern, newBoard, i, myColor(game))) {

                if (depth >= 0) {
                    int[] nextMove = min(game, depth - 1, alpha, beta);

                    if (maximum[0] == -1 || nextMove[1] > maximum[1]) {
                        maximum[0] = i;
                        maximum[1] = nextMove[1];
                        alpha = nextMove[1];
                    }


                    if (alpha >= beta) return maximum;

                }

            }

        }

        return maximum;

    }


    private int[] min(Game game, int depth, int alpha, int beta) {

        System.out.println("in min");

        System.out.println(game.isFinished());

        if (game.isFinished()) {
            int[] zero = {};
            return zero;
        }

        int[] minimum = {-1, 99999};

        for (int i = 0; i < game.getBoard().get(0).size(); i++) {

            ArrayList<ArrayList<String>> newBoard = (ArrayList<ArrayList<String>>) game.getBoard().clone();
            // ArrayList<ArrayList<String>> newBoard = new ArrayList<>();
            // Collections.copy(newBoard, game.getBoard());

            Game intern = new Game(game.getWinner(), game.getCurrentPlayerId(), game.isFinished(), game.getPlayers(), newBoard);

            if (checkAndSetPosition(intern, newBoard, i, otherColor(game))) {

                if (depth >= 0) {
                    int[] nextMove = max(game, depth - 1, alpha, beta);

                    if (minimum[0] == -1 || nextMove[1] < minimum[1]) {

                        minimum[0] = i;
                        minimum[1] = nextMove[1];
                        beta = nextMove[1];

                    }
                }



                if (alpha >= beta) return minimum;

            }

        }

        return minimum;
    }


    private Boolean checkAndSetPosition(Game game, ArrayList<ArrayList<String>> board, int column, String color) {
        boolean posi = false;

        if (!game.isFinished()) {

            int row = 0;

            while (row < 6 && board.get(row).get(column).equals(EMPTY_CELL)) {

                posi = true;

                board.get(row).set(column, color);

                if (row != 0) {
                    board.get(row - 1).set(column, EMPTY_CELL);
                }

                row++;
            }
        }

        if (areFourConnected(color, board)) game.finished = true;

        return posi;
    }

    private boolean areFourConnected(String color, ArrayList<ArrayList<String>> board) {
        // horizontalCheck
        for (int row = 0; row < board.size() - 3; row++) {
            for (int column = 0; column < board.get(0).size(); column++) {
                if (board.get(row).get(column).equals(color)
                        && board.get(row + 1).get(column).equals(color)
                        && board.get(row + 2).get(column).equals(color)
                        && board.get(row + 3).get(column).equals(color)) {
                    return true;
                }
            }
        }
        // verticalCheck
        for (int row = 0; row < board.size(); row++) {
            for (int column = 0; column < board.get(0).size() - 3; column++) {
                if (board.get(row).get(column).equals(color)
                        && board.get(row).get(column + 1).equals(color)
                        && board.get(row).get(column + 2).equals(color)
                        && board.get(row).get(column + 3).equals(color)) {
                    return true;
                }
            }
        }
        // ascendingDiagonalCheck
        for (int row = 3; row < board.size(); row++) {
            for (int column = 0; column < board.get(0).size() - 3; column++) {
                if (board.get(row).get(column).equals(color)
                        && board.get(row - 1).get(column + 1).equals(color)
                        && board.get(row - 2).get(column + 2).equals(color)
                        && board.get(row - 3).get(column + 3).equals(color))
                    return true;
            }
        }
        // descendingDiagonalCheck
        for (int row = 3; row < board.size(); row++) {
            for (int column = 3; column < board.get(0).size(); column++) {
                if (board.get(row).get(column).equals(color)
                        && board.get(row - 1).get(column - 1).equals(color)
                        && board.get(row - 2).get(column - 2).equals(color)
                        && board.get(row - 3).get(column - 3).equals(color))
                    return true;
            }
        }
        return false;
    }

}
