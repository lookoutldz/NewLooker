package looko.looker.release.pool;

import looko.looker.release.api.GetGameSchema;
import looko.looker.release.entity.GameSchema;
import looko.looker.release.service.DB_GameSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskForGameSchema {

    @Autowired
    private GetGameSchema getGameSchema;
    @Autowired
    private DB_GameSchemaService gSchemaService;

    public void go(int appid){
        List<GameSchema> gameSchemas = getGameSchema.get(appid);
        if (gameSchemas.size() > 0){
            gSchemaService.updateGameSchema(gameSchemas);
        }
    }
}
