package looko.looker.release.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class GetSteamLevel {

    @Autowired
    SendRequest sendRequest;

    public int get(String steamid){

        int level = 0;
        InputStream is = sendRequest.sendGet(APIs.GetSteamLevel+"&steamid="+steamid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonObject responseObj = root.get("response").getAsJsonObject();
            if (responseObj.has("player_level")){
                level = responseObj.get("player_level").getAsInt();
            }
        }
        return level;
    }
}
