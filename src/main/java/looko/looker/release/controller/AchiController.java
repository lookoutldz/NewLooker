package looko.looker.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AchiController {

    @RequestMapping("/achievements")
    public ModelAndView achievementsPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("achievements");
        return modelAndView;
    }
}
