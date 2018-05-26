package looko.looker.release.tool;

import looko.looker.release.entity.FriendModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountOnlineFriends {

    public static Map<String, Integer> count(List<FriendModel> friendModels){

        Map<String, Integer> map = new HashMap<>();

        int online = 0, offline = 0, ingame = 0;
        if (friendModels.size() > 0){
            for (FriendModel friendModel : friendModels){
                if (friendModel.getPersonastate() == 0){
                    map.put("offline",offline++);
                }
                else {
                    map.put("online",online++);
                    if (friendModel.getGameid() != null){
                        map.put("ingame",ingame++);
                    }
                }
            }
        }

        return map;
    }
}
