package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.*;
import looko.looker.release.service.*;
import looko.looker.release.tool.ResolveScreenshot;
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

    @RequestMapping("/profile")
    public ModelAndView profilePage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Player player = playerService.findPlayerById(steamid);
        List<OwnedGame> games = ownedGameService.findFavoriteById(steamid);
        List<OwnedGame> favoriteGames;
        if (games.size() > 10){
            favoriteGames = games.subList(0,10);
        }
        else {
            favoriteGames = games;
        }
        App app;
        List<List<String>> lists = new ArrayList<>();
        for (OwnedGame game : favoriteGames){
            app = appService.findAppById(game.getAppid());
            lists.add(ResolveScreenshot.resolve(app));
        }

        List<Player> players = playerService.findFriendAsPlayer(steamid);
        List<Player> friendAsPlayer;
        if (players.size() > 10){
            friendAsPlayer = players.subList(0,10);
        }
        else {
            friendAsPlayer = players;
        }
        List<AchiModel> achis = achiService.findMyAchiDetail(steamid);
        List<AchiModel> achi_recently;
        if (achis.size() > 5){
            achi_recently = achis.subList(0,5);
        }
        else {
            achi_recently = achis;
        }

        int friend_count = friendService.countFriends(steamid);
        int achi_count = achiService.countAchieved(steamid);

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

        return modelAndView;
    }
}
