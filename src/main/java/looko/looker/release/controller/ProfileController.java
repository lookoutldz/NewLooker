package looko.looker.release.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProfileController {

    /**
     * login ajax
     */
    @RequestMapping(value = "/goLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> login(HttpServletRequest request, HttpServletResponse response){
        Map<String, Integer> map = new HashMap<>();

        return map;
    }
}
