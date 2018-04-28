package looko.looker.release.controller;

import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_OwnedGameService ownedGameService;
    @Autowired
    DB_PlayerAchiService achiService;

    @RequestMapping("/profile")
    public String profilePage(@ModelAttribute("steamid") String steamid, ModelAndView modelAndView){
        if (steamid == null)
            return "index";


        return "profile";
    }
}
