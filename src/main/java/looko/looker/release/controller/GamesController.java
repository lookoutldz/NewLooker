package looko.looker.release.controller;

import looko.looker.release.api.GetNumOfCurrentPlayer;
import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.*;
import looko.looker.release.pool.TaskForAppInfo;
import looko.looker.release.pool.TaskForFriendsGame;
import looko.looker.release.service.*;
import looko.looker.release.tool.CountHoursGames;
import looko.looker.release.tool.ResolveScreenshot;
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

    @RequestMapping("")
    public ModelAndView gamesPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Player player = playerService.findPlayerById(steamid);
        List<OwnedGame> ownedgames = ownedGameService.findFavoriteById(steamid);
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
        List<Integer> playtime_list = CountHoursGames.findByList(ownedgames);
        List<OwnedGame> perfectgames = ownedGameService.findPerfectGame(steamid);
        List<OwnedGame> recentlygames = getRecentlyGames.get(steamid);
        int playtime_forever = ownedGameService.sumPlayedTime(steamid);
        int playtime_2weeks = ownedGameService.sumPlayedTime2Week(steamid);

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
        return modelAndView;
    }

    @RequestMapping("/game")
    public ModelAndView gamePage(@ModelAttribute("steamid") String steamid, @ModelAttribute("appid") int appid){

        ModelAndView modelAndView = new ModelAndView();

        taskForAppInfo.go(appid);
        Player player = playerService.findPlayerById(steamid);
        OwnedGame myGame = ownedGameService.findOwnedGameByPriKey(steamid,appid);
        List<GameRankModel> ranks = ownedGameService.findRankGame(steamid,appid);
        List<PlayerAchi> playerAchis = achiService.findAllAchisByGame(steamid,appid);
        List<String> pic_list = ResolveScreenshot.resolve(appService.findAppById(appid));
        int playedTime = ownedGameService.sumPlayedTime(steamid);
        int playedTime2Week = ownedGameService.sumPlayedTime2Week(steamid);
        int currentPlayer = getCurrentPlayer.get(appid);
        int count_achieved = achiService.countAchievedByGame(steamid,appid);
        int count_achi = achiService.countAllAchisByGame(steamid,appid);
        App app = appService.findAppById(appid);

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
