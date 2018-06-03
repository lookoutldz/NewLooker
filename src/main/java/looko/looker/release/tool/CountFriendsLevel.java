package looko.looker.release.tool;

import looko.looker.release.entity.FriendModel;

import java.util.ArrayList;
import java.util.List;

public class CountFriendsLevel {

    public static List<Integer> count(List<FriendModel> friendModels){

        List<Integer> list = new ArrayList<>(9);

        if (friendModels.size() > 0){
            int lv0 = 0, lv10 = 0, lv50 = 0, lv100 = 0, lv200 = 0, lv300 = 0,lv500 = 0,lv1000 = 0, lvhaha = 0;
            for (FriendModel friendModel : friendModels){
                int steamlevel = friendModel.getSteamlevel();
                if (steamlevel == 0){
                    lv0++;
                }
                else if (steamlevel < 10){
                    lv10++;
                }
                else if (steamlevel < 50){
                    lv50++;
                }
                else if (steamlevel < 100){
                    lv100++;
                }
                else if (steamlevel < 200){
                    lv200++;
                }
                else if (steamlevel < 300){
                    lv300++;
                }
                else if (steamlevel < 500){
                    lv500++;
                }
                else if (steamlevel < 1000){
                    lv1000++;
                }
                else{
                    lvhaha++;
                }
            }
            list.add(lv0);
            list.add(lv10);
            list.add(lv50);
            list.add(lv100);
            list.add(lv200);
            list.add(lv300);
            list.add(lv500);
            list.add(lv1000);
            list.add(lvhaha);
        }

        return list;
    }
}
