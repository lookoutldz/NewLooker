package looko.looker.release.controller;

import looko.looker.release.api.*;
import looko.looker.release.entity.Friend;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.entity.PlayerAchi;
import looko.looker.release.pool.TaskForAchi;
import looko.looker.release.service.DB_FriendService;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    ResolveVanityURL resolveURL;
    @Autowired
    GetPlayerSummaries getPlayer;
    @Autowired
    GetSteamLevel getSteamLevel;
    @Autowired
    GetOwnedGame getOwnedGame;
    @Autowired
    GetFriendList getFriendList;
    @Autowired
    GetPlayerAchi getPlayerAchi;

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_OwnedGameService oGameService;
    @Autowired
    DB_FriendService friendService;
    @Autowired
    DB_PlayerAchiService achiService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request, HttpServletResponse response){
        return "login";
    }

    /**
     * login ajax
     */
    @RequestMapping(value = "/loginBySID", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginBySID(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        String steamid = request.getParameter("steamid");
        Player player = getPlayer.get(steamid);
        int visistate = player.getCommunityvisibilitystate();
        playerService.updatePlayer(player);
        map.put("resultCode", visistate);
        return map;
    }

    @RequestMapping(value = "/loginByVURL", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginByVURL(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        String vanityurl = request.getParameter("vanityurl");
        String steamid = resolveURL.resolve(vanityurl);
        Player player = getPlayer.get(steamid);
        int visistate = player.getCommunityvisibilitystate();
        playerService.updatePlayer(player);
        map.put("resultCode", visistate);
        return map;
    }

    /**
     * login success
     * @param request
     * @param attr
     * @return
     */
    @RequestMapping("/goLogin")
    public String goLogin(HttpServletRequest request, RedirectAttributes attr){
        String steamid = request.getParameter("input_text");
        if (steamid == null)
            return "error";
        /*
            1.更新steamlevel
            2.更新ownedgames
            3.更新friends
            4.更新achievements
         */
        long time1 = System.currentTimeMillis();

        Player player = new Player();
        player.setSteamlevel(getSteamLevel.get(steamid));
        playerService.updateExtra(player);

        long time2 = System.currentTimeMillis();

        List<OwnedGame> ownedGames = getOwnedGame.get(steamid);
        oGameService.updateOwnedGame(ownedGames);

        long time3 = System.currentTimeMillis();

        List<Friend> friends = getFriendList.getAsFriends(steamid);
        friendService.updateFriendList(friends);

        long time4 = System.currentTimeMillis();

        logger.warn("game.size="+ownedGames.size());
        if (ownedGames.size() > 0){
            for (OwnedGame ownedGame : ownedGames){
                new TaskForAchi(steamid,ownedGame).start();
            }
        }

        long time5 = System.currentTimeMillis();

        logger.warn("update player : " + (time2-time1) + "ms");
        logger.warn("update ownedgames : " + (time3-time2) + "ms");
        logger.warn("update friends : " + (time4-time3) + "ms");
        logger.warn("update achievements : " + (time5-time4) + "ms");
        logger.warn("update total : " + (time5-time1) + "ms");
        attr.addFlashAttribute("steamid",steamid);
        return "redirect:/profile";
    }

}
