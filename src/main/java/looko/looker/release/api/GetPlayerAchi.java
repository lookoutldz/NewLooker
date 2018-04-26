package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.PlayerAchi;
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
public class GetPlayerAchi {

    @Autowired
    SendRequest sendRequest;

    public List<PlayerAchi> get(String steamid, int appid){

        List<PlayerAchi> playerAchis = new ArrayList<>();

        InputStream is = sendRequest.sendGet(APIs.GetPlayerAchi+"&l=schinese&steamid="+steamid+"&appid="+appid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonObject playerstats = root.get("playerstats").getAsJsonObject();
            if (playerstats.has("achievements")){
                JsonArray achievements = playerstats.get("achievements").getAsJsonArray();
                if(achievements.size() > 0){
                    PlayerAchi playerAchi;
                    for (JsonElement jsonElement : achievements){
                        JsonObject object = jsonElement.getAsJsonObject();
                        playerAchi = new PlayerAchi();
                        playerAchi.setSteamid(steamid);
                        playerAchi.setAppid(appid);
                        playerAchi.setAchname(object.get("apiname").getAsString());
                        playerAchi.setAchieved(object.get("achieved").getAsInt());
                        playerAchi.setUnlocktime(object.get("unlocktime").getAsInt());
                        playerAchis.add(playerAchi);
                    }
                }
            }
        }
        return playerAchis;
    }
}
