package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_FriendService;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import looko.looker.release.tool.ResolveScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/profile")
    public ModelAndView profilePage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("index");
            return modelAndView;
        }

        Player player = playerService.findPlayerById(steamid);
        List<OwnedGame> games = ownedGameService.findFavoriteById(steamid);
        List<OwnedGame> favoriteGames;
        if (games.size() > 10){
            favoriteGames = games.subList(0,9);
            for (int i = 0; i < 10; i++){
                System.out.printf("i="+i+"\tappname : "+games.get(i).getAppname()+"\n");
            }
        }
        else {
            favoriteGames = games;
        }

        List<Player> players = playerService.findFriendAsPlayer(steamid);
        List<Player> friendAsPlayer;
        if (players.size() > 10){
            friendAsPlayer = players.subList(0,9);
        }
        else {
            friendAsPlayer = players;
        }

        int friend_count = friendService.countFriends(steamid);
        int achi_count = achiService.countAchieved(steamid);

        modelAndView.setViewName("blank");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("favoriteGames",favoriteGames);
        modelAndView.addObject("friendAsPlayer",friendAsPlayer);
        modelAndView.addObject("ownedgame_count",games.size());
        modelAndView.addObject("friend_count",friend_count);
        modelAndView.addObject("achi_count",achi_count);

        return modelAndView;
    }
}
