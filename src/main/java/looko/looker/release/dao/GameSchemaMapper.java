package looko.looker.release.dao;

import looko.looker.release.entity.GameSchema;
import looko.looker.release.entity.GameSchemaKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSchemaMapper {

    int deleteByPrimaryKey(GameSchemaKey key);

    int insert(GameSchema record);

    int insertSelective(GameSchema record);

    GameSchema selectByPrimaryKey(GameSchemaKey key);

    List<GameSchema> selectByAppId(Integer appid);

    int updateByPrimaryKeySelective(GameSchema record);

    int updateByPrimaryKey(GameSchema record);
}