package ch.lukasakermann.connectfourchallenge.game;

import ch.lukasakermann.connectfourchallenge.connectFourService.ConnectFourAdapter;
import ch.lukasakermann.connectfourchallenge.connectFourService.Game;
import ch.lukasakermann.connectfourchallenge.connectFourService.GameId;
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


public class GameRunner implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRunner.class);
    private static final int POLLING_IN_MILLIS = 0;
    private final ConnectFourAdapter connectFourAdapter;
    private final String playerId;
    private final int numberOfGames;
    private final ConnectFourStrategy connectFourStrategy;


    public GameRunner(ConnectFourAdapter connectFourAdapter, String playerId, ConnectFourStrategy connectFourStrategy, int numberOfGames) {
        this.connectFourAdapter = connectFourAdapter;
        this.playerId = playerId;
        this.connectFourStrategy = connectFourStrategy;
        this.numberOfGames = numberOfGames;
    }

    @Override
    public void run() {
        LocalDateTime startTime = LocalDateTime.now();
        int gamesWon = 0;
        int gamesDraw = 0;
        try {
            for (int i = 0; i < numberOfGames; i++) {
                GameId join = connectFourAdapter.getJoin(playerId);
                while (join.gameId() == null) {
                    Thread.sleep(POLLING_IN_MILLIS);
                    join = connectFourAdapter.getJoin(playerId);
                }
                UUID gameId = join.gameId();

                Game game = connectFourAdapter.getGame(gameId);
                while (!game.isFinished()) {
                    if (game.getCurrentPlayerId().equals(playerId)) {
                        connectFourAdapter.dropDisc(gameId, connectFourStrategy.dropDisc(game), playerId);
                    }
                    Thread.sleep(POLLING_IN_MILLIS);
                    game = connectFourAdapter.getGame(gameId);
                }
                if (game.getWinner() == null) {
                    connectFourStrategy.draw(game);
                    gamesDraw += 1;
                } else if (playerId.equals(game.getWinner())) {
                    connectFourStrategy.win(game);
                    gamesWon += 1;
                } else if (!playerId.equals(game.getWinner())) {
                    connectFourStrategy.loose(game);
                }
            }
            LocalDateTime endTime = LocalDateTime.now();
            long durationInSeconds = Duration.between(startTime, endTime).getSeconds();
            LOGGER.info("Games are finished: {}, won {}, draw {}, duration {}s", playerId, gamesWon, gamesDraw, durationInSeconds);
        } catch (InterruptedException e) {
            LOGGER.error("Games are interrupted: {}", e);
        } catch (Exception e) {
            LOGGER.error("Games are interrupted: {}", e);
        }
    }
}
