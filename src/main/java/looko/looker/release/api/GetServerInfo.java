package looko.looker.release.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class GetServerInfo {

    @Autowired
    SendRequest sendRequest;

    public int getServerTime(){

        int nowTime = 0;
        InputStream is = sendRequest.sendGet(APIs.GetServerInfo);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            nowTime = root.get("servertime").getAsInt();
        }
        return nowTime;
    }
}
