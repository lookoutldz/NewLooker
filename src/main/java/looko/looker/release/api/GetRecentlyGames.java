package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetRecentlyGames {

    @Autowired
    SendRequest sendRequest;

    public List<OwnedGame> get(String steamid){

        List<OwnedGame> recentlyGames = new ArrayList<>();
        InputStream is = sendRequest.sendGet(APIs.GetRecentlyPlayedGames+"&steamid="+steamid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonArray games = root.get("response").getAsJsonObject().get("games").getAsJsonArray();
            if (games.size() > 0){
                OwnedGame recentlyGame;
                JsonObject object;
                for (JsonElement element : games){
                    object = element.getAsJsonObject();
                    recentlyGame = new OwnedGame();
                    recentlyGame.setSteamid(steamid);
                    recentlyGame.setAppid(object.get("appid").getAsInt());
                    recentlyGame.setAppname(object.get("name").getAsString());
                    recentlyGame.setPlaytime2week(object.get("playtime_2weeks").getAsInt());
                    recentlyGame.setPlaytimeForever(object.get("playtime_forever").getAsInt());
                    recentlyGame.setImgIconUrl(object.get("img_icon_url").getAsString());
                    recentlyGame.setImgLogoUrl(object.get("img_logo_url").getAsString());
                    recentlyGames.add(recentlyGame);
                }
            }
        }
        return recentlyGames;
    }
}
