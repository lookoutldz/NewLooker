package looko.looker.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String profilePage(@ModelAttribute("steamid") String steamid){
        if (steamid != null)
            System.out.printf("steamid=" + steamid);
        System.out.printf("success");
        return "profile";
    }
}
