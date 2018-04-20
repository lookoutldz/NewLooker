package looko.looker.release.service;

import looko.looker.release.entity.App;

import java.util.List;

public interface DB_AppService {

    int updateAppList(List<App> apps);

    int updateAppInfo(List<App> apps);

    App findAppById(int appid);

    List<App> findAppsByName(String appname);

    int emptyAppList();
}
