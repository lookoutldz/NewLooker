package looko.looker.release.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
 */
@Component
public class CrawlerForPicAndPrice {

    /**
     * 逻辑上请求仍有4类
     * 1.可直接获取图片的游戏，如412830
     * 2.有第一类302重定向的游戏，如637650
     * 3.有第二类302重定向的游戏，如292030
     * 4.其它类（已经失效或者错误的游戏id，如000000，或者还未发现处理方法的）
     * @param appid 游戏应用的id
     * @return list<Object> 其中第一个元素为logo图片链接，第二个为滚动图集链接，第三个为价格
     */
    public List<Object> get(int appid){

        List<Object> list = new ArrayList<>();
        String headerPic = null, largePic = null;
        int price = 0;
        try
        {
            String url_str = "http://store.steampowered.com/app/" + appid;
            //获取连接的response
            Connection con = Jsoup.connect(url_str).header("Accept-Language","zh-CN,zh;q=0.9").method(Connection.Method.GET);
            Connection.Response response = con.execute();
            String url_current = response.url().toString();

            //判断类型
            if (url_current != null && url_current.equals(url_str)){
                //the way1，直接获取
                Document doc = response.parse();
                headerPic = findHeaderPic(doc);
                largePic = findLargePic(doc);
                price = findPrice(doc);
            }
            else if (url_current != null && url_current.equals("http://store.steampowered.com/agecheck/app/"+appid+"/")){
                //the way2，获取所需的参数，加到新请求的cookie或参数中post发送
                //String steamCountry = response.cookie("steamCountry");
                String browserid = response.cookie("browserid");
                String sessionid = response.cookie("sessionid");
                String birthtime = "725817601";
                String lastagecheckage = "1-January-1993";

                con = Jsoup.connect(url_current).header("Accept-Language","zh-CN,zh;q=0.9");
                con.cookie("browserid",browserid).cookie("sessionid",sessionid).cookie("birthtime",birthtime).cookie("lastagecheckage",lastagecheckage);
                con.data("snr","1_agecheck_agecheck__age-gate").data("sessionid",sessionid).data("ageDay","1").data("ageMonth","1").data("ageYear","1993");
                Document doc = con.post();
                headerPic = findHeaderPic(doc);
                largePic = findLargePic(doc);
                price = findPrice(doc);
            }
            else if (url_current != null && url_current.equals(url_str+"/agecheck")){
                //the way3，获取所需参数，加到新请求的cookie中get发送
                //String steamCountry = response.cookie("steamCountry");
                String browserid = response.cookie("browserid");
                String sessionid = response.cookie("sessionid");
                String mature_content = "1";

                con = Jsoup.connect(url_current).header("Accept-Language","zh-CN,zh;q=0.9");
                con.cookie("browserid",browserid).cookie("sessionid",sessionid).cookie("mature_content",mature_content);
                Document doc = con.get();
                headerPic = findHeaderPic(doc);
                largePic = findLargePic(doc);
                price = findPrice(doc);
            }
            else {
                //solve the problem
                System.out.print("what? the way4?\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        list.add(headerPic);
        list.add(largePic);
        list.add(price);
        return list;
    }

    /**
     * 从文档中找出steam滚动大图链接的方法
     * @param doc Jsoup文档类型的变量
     * @return String 以;分隔的多个图片链接的字符串
     */
    private String findLargePic(Document doc){

        StringBuilder builder = new StringBuilder();
        String regex = "(?=https://steamcdn).*";
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
