package looko.looker.release.pool;

import looko.looker.release.crawler.CrawlerForPicAndPrice;
import looko.looker.release.entity.App;
import looko.looker.release.service.DB_AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskForAppInfo{

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CrawlerForPicAndPrice crawler;
    @Autowired
    DB_AppService appService;

    @Async("taskExecutor")
    public void go(int appid) {

        List<Object> info = crawler.get(appid);
        if (info.size() > 0){
            App app = new App();
            app.setAppid(appid);
            app.setPicLogobar((String) info.get(0));
            app.setPicScreenshot((String) info.get(1));
            app.setPrice((Integer) info.get(2));
            appService.updateAppInfo(app);
        }
    }
}
