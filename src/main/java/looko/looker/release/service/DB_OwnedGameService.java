package looko.looker.release.service;

import looko.looker.release.entity.OwnedGame;

import java.util.List;

public interface DB_OwnedGameService {

    int updateOwnedGame(List<OwnedGame> ownedGames);

    OwnedGame findOwnedGameByPriKey(String steamid, int appid);

    OwnedGame findGameBySteamidAppname(String steamid, String appname);

    List<OwnedGame> findOwnedGamesById(String steamid);

    List<OwnedGame> findFavoriteById(String steamid);

    List<OwnedGame> findPlayedById(String steamid);

    List<OwnedGame> findNotPlayedById(String steamid);

    List<OwnedGame> findPerfectGame(String steamid);

    int emptyOwnedGame();
}
