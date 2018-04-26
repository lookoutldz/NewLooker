package looko.looker.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String profilePage(@ModelAttribute("steamid") String steamid){
        if (steamid != null)
            System.out.printf("steamid=" + steamid + "\n");

        return "profile";
    }
}
