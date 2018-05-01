package looko.looker.release.service.impl;

import looko.looker.release.dao.FriendMapper;
import looko.looker.release.entity.Friend;
import looko.looker.release.entity.FriendKey;
import looko.looker.release.tool.FindListsDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_FriendService")
public class DB_FriendServiceImpl implements looko.looker.release.service.DB_FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public int updateFriendList(List<Friend> friends) {
        int row = 0;
        if (friends.size() > 0){
            //若有数据则找到要改动的数据进行操作，若无数据则直接插入
            List<Friend> oldlist = friendMapper.selectByMyId(friends.get(0).getSteamid());
            if (oldlist.size() > 0){
                //调用工具类FindListsDiff找出需要增加或删除的数据，分别存入toAdd和toDel中，由dao进行操作
                List<List<Friend>> re = FindListsDiff.checkFriends(oldlist,friends);
                List<Friend> toAdd = re.get(0), toDel = re.get(1);
                if (toAdd.size() > 0){
                    for (Friend friend : toAdd){
                        row += friendMapper.insert(friend);
                    }
                }
                if (toDel.size() > 0){
                    FriendKey prikey;
                    String steamid = friends.get(0).getSteamid();
                    for (Friend friend : toDel){
                        prikey = new FriendKey();
                        prikey.setSteamid(steamid);
                        prikey.setFriendsteamid(friend.getFriendsteamid());
                        row += friendMapper.deleteByPrimaryKey(prikey);
                    }
                }
            }
            else {
                for (Friend friend : friends){
                    row += friendMapper.insert(friend);
                }
            }
        }
        return row;
    }

    @Override
    public Friend findFriendByPriKey(String steamid, String fsteamid) {

        FriendKey prikey = new FriendKey();
        prikey.setSteamid(steamid);
        prikey.setFriendsteamid(fsteamid);
        return friendMapper.selectByPrimaryKey(prikey);
    }

    @Override
    public List<Friend> findFriendsByMyId(String steamid) {

        return friendMapper.selectByMyId(steamid);
    }

    @Override
    public List<Friend> findOldFriendsByMyId(String steamid) {

        return friendMapper.selectOldFriendByMyId(steamid);
    }

    @Override
    public int countFriends(String steamid) {

        return friendMapper.countFriends(steamid);
    }

    @Override
    public int delFriendsByMyId(String steamid) {

        return friendMapper.deleteByMyId(steamid);
    }

    @Override
    public int emptyFriendList() {
        return 0;
    }
}
