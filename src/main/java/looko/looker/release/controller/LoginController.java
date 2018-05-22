package looko.looker.release.controller;

import looko.looker.release.api.*;
import looko.looker.release.entity.Friend;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.pool.TaskForAchi;
import looko.looker.release.pool.TaskForAppInfo;
import looko.looker.release.pool.TaskForFriendAsPlayer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TaskForAppInfo taskForAppInfo;
    @Autowired
    TaskForFriendAsPlayer taskForFriendAsPlayer;
    @Autowired
    TaskForAchi taskForAchi;

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
     * 获取到steamid，校验存在性，存在则更新数据库player表并返回相应的resultCode和steamid，否则返回相应的resultCode
     */
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginByVURL(HttpServletRequest request, HttpServletResponse response){

        Map<String, Object> map = new HashMap<>();

        String login_type = request.getParameter("login_type");
        String steamid;
        if (login_type.equals("vanityurl")){
            String vanityurl = request.getParameter("input_text");
            steamid = resolveURL.resolve(vanityurl);
        }
        else {
            steamid = request.getParameter("input_text");
        }

        Player player = getPlayer.get(steamid);
        if (player.getSteamid() != null){
            playerService.updatePlayer(player);
            int visistate = player.getCommunityvisibilitystate();
            logger.warn("code="+visistate);
            map.put("resultCode", visistate);
            map.put("player_data",steamid);
        }
        else {
            map.put("resultCode",-1);
        }
        return map;
    }

    /**
     * check success, go login
     * @param request
     * @param attr
     * @return
     */
    @RequestMapping(value = "/goLogin", method = RequestMethod.POST)
    public String goLogin(HttpServletRequest request, RedirectAttributes attr){

        String steamid = request.getParameter("hidden_steamid");
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
        player.setSteamid(steamid);
        player.setSteamlevel(getSteamLevel.get(steamid));
        playerService.updateExtra(player);


        long time2 = System.currentTimeMillis();

        List<OwnedGame> ownedGames = getOwnedGame.get(steamid);
        oGameService.updateOwnedGame(ownedGames);
        List<OwnedGame> favorite = oGameService.findFavoriteById(steamid);
        if (favorite.size() > 0){
            int i = 1;
            for (OwnedGame game : favorite){
                taskForAppInfo.go(game.getAppid());
                if (i >= 5)
                    break;
                i++;
            }
        }

        long time3 = System.currentTimeMillis();

        List<Friend> friends = getFriendList.getAsFriends(steamid);
        friendService.updateFriendList(friends);
        if (friends.size() > 0){
            for (Friend friend : friends){
                taskForFriendAsPlayer.go(friend.getFriendsteamid());
            }
        }

        long time4 = System.currentTimeMillis();

        logger.warn("game.size="+ownedGames.size());
        if (ownedGames.size() > 0){
            for (OwnedGame ownedGame : ownedGames){
                taskForAchi.go(steamid,ownedGame);
            }
        }

        long time5 = System.currentTimeMillis();

        logger.info("update player : " + (time2-time1) + "ms");
        logger.info("update ownedgames : " + (time3-time2) + "ms");
        logger.info("update friends : " + (time4-time3) + "ms");
        logger.info("update achievements : " + (time5-time4) + "ms");
        logger.info("update total : " + (time5-time1) + "ms");
        attr.addAttribute("steamid",steamid);

        return "redirect:/profile";
    }

}
