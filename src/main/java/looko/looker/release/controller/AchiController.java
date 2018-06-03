package looko.looker.release.controller;

import looko.looker.release.entity.AchiModel;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AchiController {

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_PlayerAchiService playerAchiService;

    @RequestMapping("/achievements")
    public ModelAndView achievementsPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        if (steamid == null) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        Player player = playerService.findPlayerById(steamid);
        List<AchiModel> achiModels = playerAchiService.findMyAchiDetail(steamid);

        modelAndView.setViewName("achievements");
        modelAndView.addObject("player",player);
        modelAndView.addObject("achiModels",achiModels);
        return modelAndView;
    }
}
