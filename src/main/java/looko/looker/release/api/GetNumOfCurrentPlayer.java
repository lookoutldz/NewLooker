package looko.looker.release.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.tool.APIs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class GetNumOfCurrentPlayer {

    @Autowired
    SendRequest sendRequest;

    public int get(int appid){

        int num = 0;
        InputStream is = sendRequest.sendGet(APIs.GetNumOfCurrentPlayers+"&appid="+appid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            num = root.get("response").getAsJsonObject().get("player_count").getAsInt();
        }
        return num;
    }
}
