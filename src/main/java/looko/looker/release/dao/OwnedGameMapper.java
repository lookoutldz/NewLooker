package looko.looker.release.dao;

import looko.looker.release.entity.GameRankModel;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.OwnedGameKey;
import looko.looker.release.entity.OwnedGameKey2;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnedGameMapper {

    int deleteByPrimaryKey(OwnedGameKey key);

    int insert(OwnedGame record);

    int insertSelective(OwnedGame record);

    OwnedGame selectByPrimaryKey(OwnedGameKey key);

    OwnedGame selectBySteamidAppname(OwnedGameKey2 key2);

    List<OwnedGame> selectByMyId(String steamid);

    List<OwnedGame> selectByMyIdFavorite(String steamid);

    List<OwnedGame> selectPlayed(String steamid);

    List<OwnedGame> selectNotPlayed(String steamid);

    List<OwnedGame> selectRecentlyGame(String steamid);

    List<OwnedGame> selectPerfectGame(String steamid);

    List<GameRankModel> selectRankGame(OwnedGameKey key);

    int sumPlayedTime(String steamid);

    int sumPlayedTime2Week(String steamid);

    int updateByPrimaryKeySelective(OwnedGame record);

    int updateByPrimaryKey(OwnedGame record);
}