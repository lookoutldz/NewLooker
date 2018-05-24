package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.api.GetServerInfo;
import looko.looker.release.entity.*;
import looko.looker.release.service.*;
import looko.looker.release.tool.ResolveScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    GetServerInfo getServerInfo;
    @Autowired
    GetRecentlyGames getRecentlyGames;
    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_OwnedGameService ownedGameService;
    @Autowired
    DB_FriendService friendService;
    @Autowired
    DB_PlayerAchiService achiService;
    @Autowired
    DB_AppService appService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/profile")
    public ModelAndView profilePage(@ModelAttribute("steamid") String steamid){

        long time1,time2,time3,time4,time5,time6,time7,time8,time9;

        time1 = System.currentTimeMillis();

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        time2 = System.currentTimeMillis();

        Player player = playerService.findPlayerById(steamid);

        time3 = System.currentTimeMillis();
        List<OwnedGame> games = ownedGameService.findFavoriteById(steamid);
        List<OwnedGame> favoriteGames;
        if (games.size() > 10){
            favoriteGames = games.subList(0,10);
        }
        else {
            favoriteGames = games;
        }

        time4 = System.currentTimeMillis();

        App app;
        List<List<String>> lists = new ArrayList<>();
        for (OwnedGame game : favoriteGames){
            app = appService.findAppById(game.getAppid());
            lists.add(ResolveScreenshot.resolve(app));
        }

        time5 = System.currentTimeMillis();

        List<Player> players = playerService.findFriendAsPlayer(steamid);
        List<Player> friendAsPlayer;
        if (players.size() > 10){
            friendAsPlayer = players.subList(0,10);
        }
        else {
            friendAsPlayer = players;
        }

        time6 = System.currentTimeMillis();

        List<AchiModel> achis = achiService.findMyAchiDetail(steamid);
        List<AchiModel> achi_recently;
        if (achis.size() > 6){
            achi_recently = achis.subList(0,6);
        }
        else {
            achi_recently = achis;
        }

        time7 = System.currentTimeMillis();

        int friend_count = friendService.countFriends(steamid);
        int achi_count = achiService.countAchieved(steamid);
        int servertime = getServerInfo.getServerTime();

        time8 = System.currentTimeMillis();

        modelAndView.setViewName("profile");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("favoriteGames",favoriteGames);
        modelAndView.addObject("friendAsPlayer",friendAsPlayer);
        modelAndView.addObject("pic_lists",lists);
        modelAndView.addObject("achi_recently",achi_recently);
        modelAndView.addObject("ownedgame_count",games.size());
        modelAndView.addObject("friend_count",friend_count);
        modelAndView.addObject("achi_count",achi_count);
        modelAndView.addObject("servertime",servertime);

        time9 = System.currentTimeMillis();

        logger.warn("建立ModelAndView："+(time2-time1)+"ms");
        logger.warn("获取当前用户信息："+(time3-time2)+"ms");
        logger.warn("获取游戏（喜好）："+(time4-time3)+"ms");
        logger.warn("获取喜爱游戏大图"+(time5-time4)+"ms");
        logger.warn("获取好友的详细信息："+(time6-time5)+"ms");
        logger.warn("获取成就详情："+(time7-time6)+"ms");
        logger.warn("获取两个数量&当前时间："+(time8-time7)+"ms");
        logger.warn("所有信息存入Model："+(time9-time8)+"ms");

        return modelAndView;
    }
}
