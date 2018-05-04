package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerService;
import looko.looker.release.tool.CountHoursGames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("")
    public ModelAndView gamesPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Player player = playerService.findPlayerById(steamid);
        List<OwnedGame> ownedgames = ownedGameService.findOwnedGamesById(steamid);
        List<Integer> playtime_list = CountHoursGames.findByList(ownedgames);
        List<OwnedGame> perfectgames = ownedGameService.findPerfectGame(steamid);
        List<OwnedGame> recentlygames = getRecentlyGames.get(steamid);

        modelAndView.setViewName("blank");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("ownedgames",ownedgames);
        modelAndView.addObject("playtime_list",playtime_list);
        modelAndView.addObject("perfectgames",perfectgames);
        modelAndView.addObject("recentlygames",recentlygames);
        return modelAndView;
    }
}
