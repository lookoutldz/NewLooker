package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetGlobalAchiPercForApp {

    @Autowired
    SendRequest sendRequest;

    public Map<String, Float> get(int appid){

        Map<String, Float> globalAchi = new HashMap<>();
        InputStream is = sendRequest.sendGet(APIs.GetGlobalAchiPercForApp+"&gameid="+appid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonArray achievements = root.get("achievementpercentages").getAsJsonObject().get("achievements").getAsJsonArray();
            if (achievements.size() > 0){
                JsonObject object;
                for (JsonElement element : achievements){
                    object = element.getAsJsonObject();
                    globalAchi.put(object.get("name").getAsString(),object.get("percent").getAsFloat());
                }
            }
        }
        return globalAchi;
    }
}
