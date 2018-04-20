package looko.looker.release.service;

import looko.looker.release.entity.GameSchema;
import looko.looker.release.entity.GameSchemaKey;

import java.util.List;

public interface DB_GameSchemaService {

    int updateGameSchema(List<GameSchema> gameSchemas);

    GameSchema findGSchemaByPriKey(int appid, String achname);

    GameSchema findGSchemaByPriKey(GameSchemaKey prikey);

    List<GameSchema> findGSchemasByAppId(int appid);

    int emptyGameSchema();
}
