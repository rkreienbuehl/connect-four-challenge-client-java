package ch.lukasakermann.connectfourchallenge.connectFourService;


import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

public interface  ConnectFourApiClient {

    @RequestLine("POST /api/v1/players/join")
    @Headers("Content-Type: application/json")
    GameId join(Join join);

    @RequestLine("GET /api/v1/players/games/{uuid}")
    Game getGame(@Param("uuid") UUID uuid);

    @RequestLine("POST /api/v1/players/games/{uuid}")
    @Headers("Content-Type: application/json")
    Game makeMove(@Param("uuid") UUID uuid, DropDisc dropDisc);
}
