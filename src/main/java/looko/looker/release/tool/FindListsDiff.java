package looko.looker.release.tool;


import looko.looker.release.entity.Friend;
import looko.looker.release.entity.OwnedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindListsDiff {

    public static List<List<Friend>> checkFriends(List<Friend> oldList, List<Friend> newList){

        Map<Friend,Integer> map = new HashMap<>(oldList.size()+newList.size());
        for (Friend friend : newList){
            map.put(friend,1);
        }
        for (Friend friend : oldList){
            if (map.get(friend)!=null){
                map.put(friend,2);
                continue;
            }
            map.put(friend,-1);
        }
        List<Friend> toAdd = new ArrayList<>();
        List<Friend> toDel = new ArrayList<>();
        for (Map.Entry<Friend, Integer> entry : map.entrySet()){
            if (1 == entry.getValue()){
                toAdd.add(entry.getKey());
            }
            else if (-1 == entry.getValue()){
                toDel.add(entry.getKey());
            }
        }
        List<List<Friend>> result = new ArrayList<>(2);
        result.add(toAdd);
        result.add(toDel);
        return result;
    }

    public static List<List<OwnedGame>> checkOwnedGames(List<OwnedGame> oldList, List<OwnedGame> newList){

        Map<OwnedGame,Integer> map = new HashMap<>(oldList.size()+newList.size());
        for (OwnedGame ownedGame : newList){
            map.put(ownedGame,1);
        }
        for (OwnedGame ownedGame : oldList){
            if (map.get(ownedGame)!=null){
                map.put(ownedGame,2);
                continue;
            }
            map.put(ownedGame,-1);
        }
        List<OwnedGame> toAdd = new ArrayList<>();
        List<OwnedGame> toDel = new ArrayList<>();
        List<OwnedGame> toUpd = new ArrayList<>();
        for (Map.Entry<OwnedGame, Integer> entry : map.entrySet()){
            if (1 == entry.getValue()){
                toAdd.add(entry.getKey());
            }
            else if (-1 == entry.getValue()){
                toDel.add(entry.getKey());
            }
            else {
                toUpd.add(entry.getKey());
            }
        }
        List<List<OwnedGame>> result = new ArrayList<>(2);
        result.add(toAdd);
        result.add(toDel);
        result.add(toUpd);
        return result;
    }

}
