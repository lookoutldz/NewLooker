package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.Player;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class GetPlayerSummaries {

    @Autowired
    SendRequest sendRequest;

    public Player get(String steamid){

        Player player = new Player();

        InputStream is = sendRequest.sendGet(APIs.GetPlayerSummaries+"&steamids="+steamid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
//            JsonArray players = parser.parse(isr).getAsJsonObject().get("response").getAsJsonObject().get("players").getAsJsonArray();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonObject responseObj = root.get("response").getAsJsonObject();
            JsonArray players = responseObj.get("players").getAsJsonArray();
            if (players.size() > 0){
                JsonObject result = players.get(0).getAsJsonObject();

                player.setSteamid(steamid);
                player.setCommunityvisibilitystate(result.get("communityvisibilitystate").getAsInt());
                player.setProfilestate(result.get("profilestate").getAsInt());
                player.setPersonaname(result.get("personaname").getAsString());
                player.setLastlogoff(result.get("lastlogoff").getAsInt());
                player.setProfileurl(result.get("profileurl").getAsString());
                player.setAvatar(result.get("avatar").getAsString());
                player.setAvatarmedium(result.get("avatarmedium").getAsString());
                player.setAvatarfull(result.get("avatarfull").getAsString());
                player.setPersonastate(result.get("personastate").getAsInt());
                player.setCommentpermission(result.get("commentpermission").getAsInt());

                if (result.has("personastateflags"))
                    player.setPersonastateflags(result.get("personastateflags").getAsInt());
                if (result.has("timecreated"))
                    player.setTimecreated(result.get("timecreated").getAsInt());
                if (result.has("realname"))
                    player.setRealname(result.get("realname").getAsString());
                if (result.has("primaryclanid"))
                    player.setPrimaryclanid(result.get("primaryclanid").getAsString());
                if (result.has("gameextrainfo"))
                    player.setGameextrainfo(result.get("gameextrainfo").getAsString());
                if (result.has("gameserverip"))
                    player.setGameserverip(result.get("gameserverip").getAsString());
                if (result.has("gameid"))
                    player.setGameid(result.get("gameid").getAsInt());
                if (result.has("loccountrycode"))
                    player.setLoccountrycode(result.get("loccountrycode").getAsString());
                if (result.has("locstatecode"))
                    player.setLocstatecode(result.get("locstatecode").getAsString());
                if (result.has("loccityid"))
                    player.setLoccityid(result.get("loccityid").getAsString());
            }
        }
        return player;
    }
}
