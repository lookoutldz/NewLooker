package looko.looker.release.dao;

import looko.looker.release.entity.AchiModel;
import looko.looker.release.entity.PlayerAchi;
import looko.looker.release.entity.PlayerAchiGameKey;
import looko.looker.release.entity.PlayerAchiKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerAchiMapper {

    int deleteByPrimaryKey(PlayerAchiKey key);

    int insert(PlayerAchi record);

    int insertSelective(PlayerAchi record);

    PlayerAchi selectByPrimaryKey(PlayerAchiKey key);

    List<PlayerAchi> selectAllAchi(String steamid);

    List<PlayerAchi> selectAchieved(String steamid);

    List<PlayerAchi> selectNotAchieve(String steamid);

    List<PlayerAchi> selectAllAchiByGame(PlayerAchiGameKey key);

    List<PlayerAchi> selectAchievedByGame(PlayerAchiGameKey key);

    List<PlayerAchi> selectNotAchieveByGame(PlayerAchiGameKey key);

    List<PlayerAchi> selectRecentlyAchi(String steamid);

    List<AchiModel> selectMyAchiModel(String steamid);

    int countAllAchiByGame(PlayerAchiGameKey key);

    int countAchievedByGame(PlayerAchiGameKey key);

    int countGameHasAchi(String steamid);

    int countAllAchi(String steamid);

    int countAchieved(String steamid);

    int countPerfectGame(String steamid);

    List<Integer> selectPerfectAppids(String steamid);

    int updateByPrimaryKeySelective(PlayerAchi record);

    int updateByPrimaryKey(PlayerAchi record);
}