package looko.looker.release.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class ResolveVanityURL {

    @Autowired
    SendRequest sendRequest;

    public String resolve(String steamname){

        String steamid = "network error";
        InputStream is = sendRequest.sendGet(APIs.ResolveVanityURL+"&vanityurl="+steamname);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            int succ = root.get("response").getAsJsonObject().get("success").getAsInt();
            if (1 == succ){
                steamid = root.get("response").getAsJsonObject().get("steamid").getAsString();
            }
            else if (42 == succ){
                steamid = "not exist";
            }
            else {
                steamid = "unknow error";
            }
        }
        return steamid;
    }
}
