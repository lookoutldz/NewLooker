package looko.looker.release.controller;

import looko.looker.release.entity.FriendModel;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_FriendService;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FriendsController {

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_FriendService friendService;

    @RequestMapping("/friends")
    public ModelAndView friendsPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        Player player = playerService.findPlayerById(steamid);
        List<FriendModel> friendModels = friendService.findFriendModelByMyId(steamid);
        if (friendModels.size() > 0){

        }

        modelAndView.setViewName("friends");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("friendModels",friendModels);

        return modelAndView;
    }
}
