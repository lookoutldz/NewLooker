package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.App;
import looko.looker.release.tool.APIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class GetAppList {

    @Autowired
    SendRequest sendRequest;

    public List<App> get(){

        List<App> apps = new ArrayList<>();

        InputStream is = sendRequest.sendGet(APIs.GetAppList);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonArray appArray = root.get("applist").getAsJsonObject().get("apps").getAsJsonArray();
            App app;
            JsonObject object;
            for (JsonElement element : appArray){
                object = element.getAsJsonObject();
                app = new App();
                app.setAppid(object.get("appid").getAsInt());
                app.setAppname(object.get("name").getAsString());
                apps.add(app);
            }
        }
        else {
            System.out.printf("Imputstream is null, GetAppList failed!\n");
        }
        return apps;
    }
}
