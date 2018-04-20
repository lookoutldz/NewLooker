package looko.looker.release.dao;

import looko.looker.release.entity.Friend;
import looko.looker.release.entity.FriendKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendMapper {

    int deleteByPrimaryKey(FriendKey key);

    int deleteByMyId(String steamid);

    int insert(Friend record);

    int insertSelective(Friend record);

    Friend selectByPrimaryKey(FriendKey key);

    List<Friend> selectByMyId(String steamid);

    List<Friend> selectOldFriendByMyId(String steamid);

    int updateByPrimaryKeySelective(Friend record);

    int updateByPrimaryKey(Friend record);
}