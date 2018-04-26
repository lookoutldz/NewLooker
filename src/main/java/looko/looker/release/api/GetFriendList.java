package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.Friend;
import looko.looker.release.tool.APIs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetFriendList {

    @Autowired
    SendRequest sendRequest;

    public List<Friend> getAsFriends(String steamid){

        List<Friend> friends = new ArrayList<>();

        InputStream is = sendRequest.sendGet(APIs.GetFriendList + "&steamid=" + steamid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonArray friendsArray = root.get("friendslist").getAsJsonObject().get("friends").getAsJsonArray();

            Friend friend;
            JsonObject object;
            for (JsonElement jsonElement : friendsArray)
            {
                object = jsonElement.getAsJsonObject();
                friend = new Friend();
                friend.setSteamid(steamid);
                friend.setFriendsteamid(object.get("steamid").getAsString());
                friend.setRelationship(object.get("relationship").getAsString());
                friend.setFriendSince(object.get("friend_since").getAsInt());
                friends.add(friend);
            }
        }
        return friends;
    }
}
