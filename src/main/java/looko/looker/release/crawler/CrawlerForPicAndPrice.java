package looko.looker.release.crawler;

import looko.looker.release.entity.App;
import looko.looker.release.service.DB_AppService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * looko
 * 2018-04-10
 * 新的获取steam游戏大图的类
 * 测试网络下：
 * 平均网络占用降低50%以上
 * 平均获取速率快约20%以上
 *
 * !!!多线程易被ban
 */
@Component
public class CrawlerForPicAndPrice {

    @Autowired
    DB_AppService appService;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 逻辑上请求仍有4类
     * 1.可直接获取图片的游戏，如412830
     * 2.有第一类302重定向的游戏(...agecheck/app/appid)，如637650
     * 3.有第二类302重定向的游戏(...app/appid/agecheck)，如292030
     * 4.其它类（已经失效或者错误的游戏id，如000000，或者还未发现处理方法的）
     * @param appid 游戏应用的id
     * @return list<Object> 其中第一个元素为logo图片链接，第二个为滚动图集链接，第三个为价格
     */
    public List<Object> get(int appid){

        List<Object> list = new ArrayList<>();

        App app = appService.findAppById(appid);
        if (app == null ||  (app.getPicLogobar() != null && app.getPicScreenshot() != null)){
            return list;
        }

        String headerPic = null, largePic = null;
        long time1=0,time2=0,time3=0,time4=0,time5=0,time6;
        int price = 0;
        try
        {
//            time1 = System.currentTimeMillis();
            
            String url_str = "http://store.steampowered.com/app/" + appid;
            //获取连接的response
            Connection con = null;
            Connection.Response response = null;

            try {
                con = Jsoup.connect(url_str).header("Accept-Language","zh-CN,zh;q=0.9").header("Connection","close").timeout(5000).method(Connection.Method.GET);
                response = con.execute();
            } catch (Exception e1) {
                try {
                    logger.warn("第一次重试...");
                    con = Jsoup.connect(url_str).header("Accept-Language","zh-CN,zh;q=0.9").header("Connection","close").timeout(5000).method(Connection.Method.GET);
                    response = con.execute();
                } catch (Exception e2) {
                    try {
                        logger.warn("第二次重试...");
                        con = Jsoup.connect(url_str).header("Accept-Language","zh-CN,zh;q=0.9").header("Connection","close").timeout(5000).method(Connection.Method.GET);
                        response = con.execute();
                    } catch (Exception e3) {
                        logger.warn("第二次重试失败！"+url_str);
                    }
                }
            }

            String url_current = response.url().toString();

            Document doc;
            //判断类型
            if (url_current != null && (url_current.equals(url_str) || url_current.equals("https://store.steampowered.com/app/"+appid))){
                //the way1，直接获取
                doc = response.parse();
            }
            else if (url_current != null && (url_current.equals("http://store.steampowered.com/agecheck/app/"+appid+"/")||url_current.equals("https://store.steampowered.com/agecheck/app/"+appid+"/"))){
                //the way2，获取所需的参数，加到新请求的cookie或参数中post发送
                //String steamCountry = response.cookie("steamCountry");
                String browserid = response.cookie("browserid");
                String sessionid = response.cookie("sessionid");
                String birthtime = "725817601";
                String lastagecheckage = "1-January-1993";

                con = Jsoup.connect(url_current).header("Accept-Language","zh-CN,zh;q=0.9");
                con.cookie("browserid",browserid).cookie("sessionid",sessionid).cookie("birthtime",birthtime).cookie("lastagecheckage",lastagecheckage);
                con.data("snr","1_agecheck_agecheck__age-gate").data("sessionid",sessionid).data("ageDay","1").data("ageMonth","1").data("ageYear","1993");
                    doc = con.post();
            }
            else if (url_current != null && (url_current.equals(url_str+"/agecheck")||url_current.equals("https://store.steampowered.com/app/"+appid+"/agecheck"))){
                //the way3，获取所需参数，加到新请求的cookie中get发送
                //String steamCountry = response.cookie("steamCountry");
                String browserid = response.cookie("browserid");
                String sessionid = response.cookie("sessionid");
                String mature_content = "1";

                con = Jsoup.connect(url_current).header("Accept-Language","zh-CN,zh;q=0.9");
                con.cookie("browserid",browserid).cookie("sessionid",sessionid).cookie("mature_content",mature_content);
                doc = con.get();
            }
            else {
                //solve the problem
                logger.warn("what? the way4?\t"+url_str+"url_currnet="+url_current);
                doc = new Document("<html></html>");
            }

            if (doc.body() != null){

//                time2 = System.currentTimeMillis();
                headerPic = findHeaderPic(doc);
//                time3 = System.currentTimeMillis();
                largePic = findLargePic(doc);
//                time4 = System.currentTimeMillis();
                price = findPrice(doc);
//                time5 = System.currentTimeMillis();

//                logger.warn("doc is ok!\theaderPic="+headerPic+"\tprice="+price);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        list.add(headerPic);
        list.add(largePic);
        list.add(price);

//        time6 = System.currentTimeMillis();
        
//        logger.info("获取网页数据耗时："+(time2-time1)+"ms");
//        logger.info("提取游戏LOGO耗时："+(time3-time2)+"ms");
//        logger.info("提取滚动图片耗时："+(time4-time3)+"ms");
//        logger.info("提取游戏价格耗时："+(time5-time4)+"ms");
//        logger.info("存入List耗时："+(time6-time5)+"ms");
        
        return list;
    }

    /**
     * 从文档中找出steam滚动大图链接的方法
     * @param doc Jsoup文档类型的变量
     * @return String 以;分隔的多个图片链接的字符串
     */
    private String findLargePic(Document doc){

        StringBuilder builder = new StringBuilder();
        String regex = "(?<=url=).*";
        Pattern pattern = Pattern.compile(regex);
        Elements elements = doc.getElementsByClass("highlight_screenshot_link");
        if (elements.size() > 0){
            int count = 0;
            for (Element e : elements) {
                Matcher matcher = pattern.matcher(e.attr("href"));
                if (matcher.find()) {
                    builder.append(matcher.group(0)).append(";");
                    count++;
                }
                if (count > 14)
                    break;
            }
        }
        return builder.toString();
    }

    /**
     * 从文档中找出steam应用大logo图标链接的方法
     * @param doc Jsoup文档类型的变量
     * @return String 单个的图片链接
     */
    private String findHeaderPic(Document doc){

        String header;
        Elements elements = doc.getElementsByClass("game_header_image_full");
        header = elements.get(0).attr("src");
        return header;
    }

    /**
     * 从文档中找到价格的方法
     * @param doc Jsoup文档类型的变量
     * @return int 价格（CNY）
     */
    private int findPrice(Document doc){

        int price = 0;
        String str_price;
        Elements elements = doc.getElementsByClass("game_purchase_action_bg");
        Element element = elements.get(0).child(0);
        if (element.hasClass("discount_block")){
            str_price = element.child(1).child(0).text();
        }
        else{
            str_price = element.text();
        }
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str_price);
        if (matcher.find()){
            price = Integer.parseInt(matcher.group(0));
        }
        return price;
    }

}
