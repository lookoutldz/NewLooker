package looko.looker.release.pool;

import looko.looker.release.crawler.CrawlerForPicAndPrice;
import looko.looker.release.entity.App;
import looko.looker.release.service.DB_AppService;
import looko.looker.release.tool.ApplicationContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class TaskForAppInfo extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CrawlerForPicAndPrice crawler = ApplicationContextHelper.getBean(CrawlerForPicAndPrice.class);
    private DB_AppService appService = ApplicationContextHelper.getBean(DB_AppService.class);

    private int appid;

    public TaskForAppInfo(int appid){
        this.appid = appid;
    }

    @Override
    @Async("executor")
    public void run() {

        List<Object> info = crawler.get(appid);
        App app = new App();
        app.setAppid(appid);
        app.setPicLogobar((String) info.get(0));
        app.setPicScreenshot((String) info.get(1));
        app.setPrice((Integer) info.get(2));
        logger.info("insert appinfo : "+appService.updateAppInfo(app));
    }
}
