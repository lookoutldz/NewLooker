package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
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
            JsonObject root = (JsonObject) parser.parse(isr);
            JsonObject responseObj = root.get("response").getAsJsonObject();
            if (responseObj.has("games")){
                JsonArray games = responseObj.get("games").getAsJsonArray();
                if (games.size() > 0){
                    OwnedGame recentlyGame;
                    String picheader = "http://media.steampowered.com/steamcommunity/public/images/apps/";
                    JsonObject object;
                    for (JsonElement element : games){
                        object = element.getAsJsonObject();
                        if (object.has("name")){
                            recentlyGame = new OwnedGame();
                            recentlyGame.setSteamid(steamid);
                            recentlyGame.setAppid(object.get("appid").getAsInt());
                            recentlyGame.setAppname(object.get("name").getAsString());
                            recentlyGame.setPlaytime2week(object.get("playtime_2weeks").getAsInt());
                            recentlyGame.setPlaytimeForever(object.get("playtime_forever").getAsInt());
                            recentlyGame.setImgIconUrl(picheader+object.get("appid")+"/"+object.get("img_icon_url").getAsString()+".jpg");
                            recentlyGame.setImgLogoUrl(picheader+object.get("appid")+"/"+object.get("img_logo_url").getAsString()+".jpg");
                            recentlyGames.add(recentlyGame);
                        }
                    }
                }
            }
        }
        return recentlyGames;
    }


    private static void saveToFile(InputStream is, String file_name){
        try
        {
            OutputStream os = new FileOutputStream(new File(file_name+".json"));
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
