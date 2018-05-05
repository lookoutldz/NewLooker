package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.App;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_AppService;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerService;
import looko.looker.release.tool.CountHoursGames;
import looko.looker.release.tool.ResolveScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @Autowired
    GetRecentlyGames getRecentlyGames;

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_OwnedGameService ownedGameService;
    @Autowired
    DB_AppService appService;

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
        int playtime_forever = ownedGameService.countGameTime(steamid);
        int playtime_2weeks = ownedGameService.countGameTime2Weeks(steamid);

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

        modelAndView.setViewName("game");
        return modelAndView;
    }
}
