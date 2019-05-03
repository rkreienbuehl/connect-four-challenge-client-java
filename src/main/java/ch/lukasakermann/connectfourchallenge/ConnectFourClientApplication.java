package ch.lukasakermann.connectfourchallenge;

import ch.lukasakermann.connectfourchallenge.connectFourService.ConnectFourAdapter;
import ch.lukasakermann.connectfourchallenge.game.GameRunner;
import ch.lukasakermann.connectfourchallenge.game.strategy.LWRStrategy;
import ch.lukasakermann.connectfourchallenge.game.strategy.RandomStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectFourClientApplication {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final int NUMBER_OF_GAMES = 1_000;

    public static void main(String[] args) {
        ConnectFourAdapter connectFourAdapter = new ConnectFourAdapter(SERVER_URL);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new GameRunner(connectFourAdapter, "Alice", new LWRStrategy(), NUMBER_OF_GAMES));
        executor.submit(new GameRunner(connectFourAdapter, "Bob", new RandomStrategy(), NUMBER_OF_GAMES));
    }
}
