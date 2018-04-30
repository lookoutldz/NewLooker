package looko.looker.release.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
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
    DB_PlayerAchiService achiService;

    @RequestMapping("/profile")
    public ModelAndView profilePage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("index");
            return modelAndView;
        }

        List<OwnedGame> favoriteGames = this.ownedGameService.findFavoriteById(steamid);

        modelAndView.setViewName("blank");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("favoriteGames",favoriteGames);
        return modelAndView;
    }
}
