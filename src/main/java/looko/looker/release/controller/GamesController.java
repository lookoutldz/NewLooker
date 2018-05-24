package looko.looker.release.controller;

import looko.looker.release.api.GetNumOfCurrentPlayer;
import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.*;
import looko.looker.release.pool.TaskForAppInfo;
import looko.looker.release.pool.TaskForFriendsGame;
import looko.looker.release.service.*;
import looko.looker.release.tool.CountHoursGames;
import looko.looker.release.tool.ResolveScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @Autowired
    GetRecentlyGames getRecentlyGames;
    @Autowired
    GetNumOfCurrentPlayer getCurrentPlayer;
    @Autowired
    TaskForFriendsGame task;
    @Autowired
    TaskForAppInfo taskForAppInfo;

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_OwnedGameService ownedGameService;
    @Autowired
    DB_AppService appService;
    @Autowired
    DB_PlayerAchiService achiService;
    @Autowired
    DB_FriendService friendService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public ModelAndView gamesPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        long time1,time2,time3,time4,time5,time6,time7,time8,time9,time10;
        time1 = System.currentTimeMillis();

        Player player = playerService.findPlayerById(steamid);

        time2 = System.currentTimeMillis();

        List<OwnedGame> ownedgames = ownedGameService.findFavoriteById(steamid);

        time3 = System.currentTimeMillis();

        List<List<String>> lists = new ArrayList<>(10);
        if (ownedgames.size() > 10){
            int i = 0;
            App app;
            for (OwnedGame game : ownedgames){
                app = appService.findAppById(game.getAppid());
                lists.add(ResolveScreenshot.resolve(app));
                if (++i >= 10)
                    break;
            }
        }
        else {
            if (ownedgames.size() > 0){
                App app;
                for (OwnedGame game : ownedgames){
                    app = appService.findAppById(game.getAppid());
                    lists.add(ResolveScreenshot.resolve(app));
                }
            }
        }

        time4 = System.currentTimeMillis();

        List<Integer> playtime_list = CountHoursGames.findByList(ownedgames);

        time5 = System.currentTimeMillis();

        List<OwnedGame> perfectgames = ownedGameService.findPerfectGame(steamid);

        time6 = System.currentTimeMillis();

        List<OwnedGame> recentlygames = getRecentlyGames.get(steamid);

        time7 = System.currentTimeMillis();

        int playtime_forever = ownedGameService.sumPlayedTime(steamid);

        time8 = System.currentTimeMillis();

        int playtime_2weeks = ownedGameService.sumPlayedTime2Week(steamid);

        time9 = System.currentTimeMillis();

        modelAndView.setViewName("games");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("ownedgames",ownedgames);
        modelAndView.addObject("pic_lists",lists);
        modelAndView.addObject("playtime_list",playtime_list);
        modelAndView.addObject("perfectgames",perfectgames);
        modelAndView.addObject("recentlygames",recentlygames);
        modelAndView.addObject("playtime_forever",playtime_forever);
        modelAndView.addObject("playtime_2weeks",playtime_2weeks);

        time10 = System.currentTimeMillis();

        logger.info("获取当前用户信息："+(time2-time1)+"ms");
        logger.info("findFavoriteById："+(time3-time2)+"ms");
        logger.info("获取5个应用的大图："+(time4-time3)+"ms");
        logger.info("库内游戏时间统计："+(time5-time4)+"ms");
        logger.info("获取全成就游戏："+(time6-time5)+"ms");
        logger.info("getRecentlyGames："+(time7-time6)+"ms");
        logger.info("游戏总时长："+(time8-time7)+"ms");
        logger.info("游戏两周时长："+(time9-time8)+"ms");
        logger.info("所有信息存入Model："+(time10-time9)+"ms");

        return modelAndView;
    }

    @RequestMapping("/game")
    public ModelAndView gamePage(@ModelAttribute("steamid") String steamid, @ModelAttribute("appid") int appid){

        ModelAndView modelAndView = new ModelAndView();

        long time1,time2,time3,time4,time5,time6,time7,time8,time9,time10,time11,time12,time13,time14;
        time1 = System.currentTimeMillis();

        taskForAppInfo.go(appid);

        time2 = System.currentTimeMillis();

        Player player = playerService.findPlayerById(steamid);

        time3 = System.currentTimeMillis();

        OwnedGame myGame = ownedGameService.findOwnedGameByPriKey(steamid,appid);

        time4 = System.currentTimeMillis();

        List<GameRankModel> ranks = ownedGameService.findRankGame(steamid,appid);

        time5 = System.currentTimeMillis();

        List<PlayerAchi> playerAchis = achiService.findAllAchisByGame(steamid,appid);

        time6 = System.currentTimeMillis();

        List<String> pic_list = ResolveScreenshot.resolve(appService.findAppById(appid));

        time7 = System.currentTimeMillis();

        int playedTime = ownedGameService.sumPlayedTime(steamid);

        time8 = System.currentTimeMillis();

        int playedTime2Week = ownedGameService.sumPlayedTime2Week(steamid);

        time9 = System.currentTimeMillis();

        int currentPlayer = getCurrentPlayer.get(appid);

        time10 = System.currentTimeMillis();

        int count_achieved = achiService.countAchievedByGame(steamid,appid);

        time11 = System.currentTimeMillis();

        int count_achi = achiService.countAllAchisByGame(steamid,appid);

        time12 = System.currentTimeMillis();

        App app = appService.findAppById(appid);

        time13 = System.currentTimeMillis();

        modelAndView.setViewName("game");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("myGame",myGame);
        modelAndView.addObject("ranks",ranks);
        modelAndView.addObject("playerAchis",playerAchis);
        modelAndView.addObject("pic_list",pic_list);
        modelAndView.addObject("playedTime",playedTime);
        modelAndView.addObject("playedTime2Week",playedTime2Week);
        modelAndView.addObject("currentPlayer",currentPlayer);
        modelAndView.addObject("count_achieved",count_achieved);
        modelAndView.addObject("count_achi",count_achi);
        modelAndView.addObject("app",app);

        time14 = System.currentTimeMillis();

        logger.warn("taskForAppInfo："+(time2-time1)+"ms");
        logger.warn("获取当前用户信息："+(time3-time2)+"ms");
        logger.warn("findRankGame："+(time4-time3)+"ms");
        logger.warn("findAllAchisByGame"+(time5-time4)+"ms");
        logger.warn("获取喜爱游戏大图："+(time6-time5)+"ms");
        logger.warn("获取成就详情："+(time7-time6)+"ms");
        logger.warn("游戏总时长："+(time8-time7)+"ms");
        logger.warn("2周游戏时长总计："+(time9-time8)+"ms");
        logger.warn("获取当前用户数："+(time10-time9)+"ms");
        logger.warn("countAchievedByGame："+(time11-time10)+"ms");
        logger.warn("countAllAchisByGame："+(time12-time11)+"ms");
        logger.warn("findAppById："+(time13-time12)+"ms");
        logger.warn("所有信息存入Model："+(time14-time13)+"ms");

        return modelAndView;
    }

    /**
     * 更新信息的ajax
     */
    @RequestMapping(value = "/updFriendsGame", method = RequestMethod.POST)
    @ResponseBody
    public int update(@ModelAttribute("steamid") String steamid){

        int row = 0;
        List<Friend> friends = friendService.findFriendsByMyId(steamid);
        if (friends.size() > 0){
            for (Friend friend : friends){
                task.go(friend.getFriendsteamid());
            }
        }
//        System.out.printf("upd over\n");
        return row;
    }
}
