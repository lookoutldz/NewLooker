package looko.looker.release.service;


import looko.looker.release.entity.AchiModel;
import looko.looker.release.entity.PlayerAchi;

import java.util.List;

public interface DB_PlayerAchiService {

    int updatePlayerAchi(List<PlayerAchi> playerAchis);

    List<PlayerAchi> findAllAchis(String steamid);

    List<PlayerAchi> findAchieved(String steamid);

    List<PlayerAchi> findNotAchieve(String steamid);

    List<PlayerAchi> findAllAchisByGame(String steamid, int appid);

    List<PlayerAchi> findAchievedByGame(String steamid, int appid);

    List<PlayerAchi> findNotAchieveByGame(String steamid, int appid);

    List<PlayerAchi> findRecentlyAchi(String steamid);

    List<AchiModel> findMyAchiDetail(String steamid);

    int countAllAchis(String steamid);

    int countAchieved(String steamid);

    int countAllAchisByGame(String steamid, int appid);

    int countAchievedByGame(String steamid, int appid);

    int countGameHasAchi(String steamid);

    int countPerfectGame(String steamid);

    List<Integer> findPerfectGameId(String steamid);

    int emptyPlayerAchi();
}
