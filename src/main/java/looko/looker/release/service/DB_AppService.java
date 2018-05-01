package looko.looker.release.service;

import looko.looker.release.entity.App;

import java.util.List;

public interface DB_AppService {

    int updateAppList(List<App> apps);

    int updateAppInfo(List<App> apps);

    int updateAppInfo(App app);

    App findAppById(int appid);

    List<App> findAppsByName(String appname);

//    List<String> findScreenShots(int appid);

    int emptyAppList();
}
