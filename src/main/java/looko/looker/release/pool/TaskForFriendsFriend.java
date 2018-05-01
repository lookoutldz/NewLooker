package looko.looker.release.pool;

import looko.looker.release.api.GetFriendList;
import looko.looker.release.entity.Friend;
import looko.looker.release.service.DB_FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskForFriendsFriend {

    @Autowired
    GetFriendList getFriendList;
    @Autowired
    DB_FriendService friendService;

    @Async("taskExecutor")
    public void go(String steamid) {
        List<Friend> friends = getFriendList.getAsFriends(steamid);
        if (friends.size() > 0){
            friendService.updateFriendList(friends);
        }
    }
}
