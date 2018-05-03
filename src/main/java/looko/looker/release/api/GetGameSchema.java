package looko.looker.release.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import looko.looker.release.entity.GameSchema;
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
public class GetGameSchema {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SendRequest sendRequest;

    public List<GameSchema> get(int appid){

        List<GameSchema> gameSchemas = new ArrayList<>();

        InputStream is = sendRequest.sendGet(APIs.GetSchemaForGame+"&l=schinese&appid="+appid);
        if (is != null){
            InputStreamReader isr = new InputStreamReader(is);
            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(isr).getAsJsonObject();
            JsonArray achievements = root.get("game").getAsJsonObject().get("availableGameStats").getAsJsonObject().get("achievements").getAsJsonArray();
            if (achievements.size() > 0){
                GameSchema gameSchema;
                JsonObject object;
                for (JsonElement element : achievements){
                    object = element.getAsJsonObject();
                    gameSchema = new GameSchema();
                    gameSchema.setAppid(appid);
                    gameSchema.setAchname(object.get("name").getAsString());
                    gameSchema.setDefaultvalue(object.get("defaultvalue").getAsInt());
                    gameSchema.setDisplayname(object.get("displayName").getAsString());
                    gameSchema.setHidden(object.get("hidden").getAsInt());
                    if (object.has("description")){
//                        logger.info("ok\t"+object.get("description").getAsString());
                        gameSchema.setDescription(object.get("description").getAsString());
                    }
                    gameSchema.setIcon(object.get("icon").getAsString());
                    gameSchema.setIcongray(object.get("icongray").getAsString());
                    gameSchemas.add(gameSchema);
                }
            }
        }
        return gameSchemas;

    }
}
