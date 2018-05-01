package looko.looker.release.service.impl;

import looko.looker.release.dao.AppMapper;
import looko.looker.release.entity.App;
import looko.looker.release.service.DB_AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_AppService")
public class DB_AppServiceImpl implements DB_AppService {

    @Autowired
    AppMapper appMapper;

    @Override
    public int updateAppList(List<App> apps) {
        int row = 0;
        if (apps.size() > 0){
            for (App app : apps){
                if (null == appMapper.selectByPrimaryKey(app.getAppid())){
                    row += appMapper.insert(app);
                }
            }
        }
        return row;
    }

    @Override
    public int updateAppInfo(List<App> apps) {
        int row = 0;
        if (apps.size() > 0){
            for (App app : apps){
                if (null == appMapper.selectByPrimaryKey(app.getAppid())){
                    row += appMapper.insertSelective(app);
                }
            }
        }
        return row;
    }

    @Override
    public int updateAppInfo(App app) {
        int row = 0;
        if (null == appMapper.selectByPrimaryKey(app.getAppid())){
            row += appMapper.insertSelective(app);
        }
        else {
            row = appMapper.updateByPrimaryKeySelective(app);
        }
        return row;
    }

    @Override
    public App findAppById(int appid) {

        return appMapper.selectByPrimaryKey(appid);
    }

    @Override
    public List<App> findAppsByName(String appname) {

        return appMapper.selectByAppname("%"+appname+"%");
    }

//    @Override
//    public List<String> findScreenShots(int appid) {
//
//        return appMapper.selectScreenShot(appid);
//    }

    @Override
    public int emptyAppList() {
        return 0;
    }
}
