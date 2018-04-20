package looko.looker.release.service;


import looko.looker.release.entity.Player;

import java.util.List;

public interface DB_PlayerService {

    int updatePlayer(Player player);

    int checkVisiState(String steamid);

    Player findPlayerById(String steamid);

    List<Player> findPlayersByName(String name);

    int emptyPlayer();
}
