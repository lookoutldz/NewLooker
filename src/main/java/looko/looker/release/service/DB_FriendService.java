package looko.looker.release.service;

import looko.looker.release.entity.Friend;
import looko.looker.release.entity.FriendModel;

import java.util.List;

public interface DB_FriendService {

    int updateFriendList(List<Friend> friends);

    Friend findFriendByPriKey(String steamid, String fsteamid);

    List<Friend> findFriendsByMyId(String steamid);

    List<Friend> findOldFriendsByMyId(String steamid);

    List<FriendModel> findFriendModelByMyId(String steamid);

    int countFriends(String steamid);

    int delFriendsByMyId(String steamid);

    int emptyFriendList();
}
