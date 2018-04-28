package looko.looker.release.pool;

import looko.looker.release.api.GetFriendList;
import looko.looker.release.entity.Friend;
import looko.looker.release.service.DB_FriendService;
import looko.looker.release.tool.ApplicationContextHelper;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class TaskForFriendsFriend extends Thread {

    private GetFriendList getFriendList = ApplicationContextHelper.getBean(GetFriendList.class);
    private DB_FriendService friendService = ApplicationContextHelper.getBean(DB_FriendService.class);

    private String steamid;

    public TaskForFriendsFriend(String steamid) {
        this.steamid = steamid;
    }

    @Override
    @Async("executor")
    public void run() {
        List<Friend> friends = getFriendList.getAsFriends(steamid);
        if (friends.size() > 0){
            friendService.updateFriendList(friends);
        }
    }
}
