package looko.looker.release.dao;

import looko.looker.release.entity.App;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppMapper {

    int deleteByPrimaryKey(Integer appid);

    int insert(App record);

    int insertSelective(App record);

    App selectByPrimaryKey(Integer appid);

    List<App> selectByAppname(String appname);

    List<String> selectScreenShot(Integer appid);

    int updateByPrimaryKeySelective(App record);

    int updateByPrimaryKey(App record);
}