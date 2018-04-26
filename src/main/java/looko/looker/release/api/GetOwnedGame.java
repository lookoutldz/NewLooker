package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.OwnedGame;
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
public class GetOwnedGame {

    @Autowired
    SendRequest sendRequest;

    public List<OwnedGame> get(String steamid){

        List<OwnedGame> ownedGames = new ArrayList<>();

        InputStream is = sendRequest.sendGet(APIs.GetOwnedGames+"&include_appinfo=1&include_played_free_games=1&steamid="+steamid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonObject responseObject = root.get("response").getAsJsonObject();
            //如果有游戏列表则添加
            if (responseObject.has("games")){
                JsonArray games = responseObject.get("games").getAsJsonArray();
                if(games.size() > 0){
                    OwnedGame ownedGame;
                    String picheader = "http://media.steampowered.com/steamcommunity/public/images/apps/";
                    for (JsonElement jsonElement : games)
                    {
                        JsonObject game = jsonElement.getAsJsonObject();
                        ownedGame = new OwnedGame();
                        ownedGame.setSteamid(steamid);
                        ownedGame.setAppid(game.get("appid").getAsInt());
                        ownedGame.setAppname(game.get("name").getAsString());
                        ownedGame.setPlaytimeForever(game.get("playtime_forever").getAsInt());
                        if (game.has("playtime_2weeks")){
                            ownedGame.setPlaytime2week(game.get("playtime_2weeks").getAsInt());
                        }
                        else {
                            ownedGame.setPlaytime2week(0);
                        }
                        if (!game.get("img_icon_url").getAsString().trim().equals("")){
                            ownedGame.setImgIconUrl(picheader+game.get("appid")+"/"+game.get("img_icon_url").getAsString()+".jpg");
                        }
                        if (!game.get("img_logo_url").getAsString().trim().equals("")){
                            ownedGame.setImgLogoUrl(picheader+game.get("appid")+"/"+game.get("img_logo_url").getAsString()+".jpg");
                        }
                        if (game.has("has_community_visible_stats")){
                            ownedGame.setHasCommunityVisibleState(game.get("has_community_visible_stats").getAsBoolean());
                        }
                        ownedGames.add(ownedGame);
                    }
                }
            }
        }
        return ownedGames;
    }
}
