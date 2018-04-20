package looko.looker.release.service.impl;

import looko.looker.release.dao.GameSchemaMapper;
import looko.looker.release.entity.GameSchema;
import looko.looker.release.entity.GameSchemaKey;
import looko.looker.release.service.DB_GameSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_GSchemaService")
public class DB_GameSchemaServiceImpl implements DB_GameSchemaService {

    @Autowired
    private GameSchemaMapper gameSchemaMapper;

    @Override
    public int updateGameSchema(List<GameSchema> gameSchemas) {
        int row = 0;
        if (gameSchemas.size() > 0){
            GameSchemaKey prikey;
            for (GameSchema gameSchema : gameSchemas){
                prikey = new GameSchemaKey();
                prikey.setAppid(gameSchema.getAppid());
                prikey.setAchname(gameSchema.getAchname());
                if (null == findGSchemaByPriKey(prikey)){
                    row += gameSchemaMapper.insert(gameSchema);
                }
            }
        }
        return row;
    }

    @Override
    public GameSchema findGSchemaByPriKey(int appid, String achname) {

        GameSchemaKey prikey = new GameSchemaKey();
        prikey.setAppid(appid);
        prikey.setAchname(achname);
        return gameSchemaMapper.selectByPrimaryKey(prikey);
    }

    @Override
    public GameSchema findGSchemaByPriKey(GameSchemaKey prikey) {

        return gameSchemaMapper.selectByPrimaryKey(prikey);
    }

    @Override
    public List<GameSchema> findGSchemasByAppId(int appid) {

        return gameSchemaMapper.selectByAppId(appid);
    }

    @Override
    public int emptyGameSchema() {
        return 0;
    }
}
