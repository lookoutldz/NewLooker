package looko.looker.release.dao;

import looko.looker.release.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerMapper {

    int deleteByPrimaryKey(String steamid);

    int insert(Player record);

    int insertSelective(Player record);

    int checkVisibilityState(String steamid);

    Player selectByPrimaryKey(String steamid);

    List<Player> selectByName(String name);

    List<Player> selectFriendAsPlayer(String steamid);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}