package looko.looker.release.controller;

import looko.looker.release.api.GetRecentlyGames;
import looko.looker.release.api.GetServerInfo;
import looko.looker.release.entity.FriendModel;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_FriendService;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerService;
import looko.looker.release.tool.CountFriendsLevel;
import looko.looker.release.tool.CountOnlineFriends;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class FriendsController {

    @Autowired
    DB_PlayerService playerService;
    @Autowired
    DB_FriendService friendService;
    @Autowired
    DB_OwnedGameService ownedGameService;

    @Autowired
    GetServerInfo getServerInfo;

    @RequestMapping("/friends")
    public ModelAndView friendsPage(@ModelAttribute("steamid") String steamid){

        ModelAndView modelAndView = new ModelAndView();

        Player player = playerService.findPlayerById(steamid);
        List<FriendModel> friendModels = friendService.findFriendModelByMyId(steamid);
        Map<String, Object> map = findPopularGames(friendModels);
        FriendModel luckyDog = (FriendModel) map.get("luckyDog");
        List<OwnedGame> popGames = (List<OwnedGame>) map.get("popGames");
        Map<String, Integer> onlineStatus = CountOnlineFriends.count(friendModels);
        List<Integer> levels = CountFriendsLevel.count(friendModels);
        int timeNow = getServerInfo.getServerTime();

        modelAndView.setViewName("friends");
        modelAndView.addObject("steamid",steamid);
        modelAndView.addObject("player",player);
        modelAndView.addObject("friendModels",friendModels);
        modelAndView.addObject("luckyDog",luckyDog);
        modelAndView.addObject("popGames",popGames);
        modelAndView.addObject("onlineStatus",onlineStatus);
        modelAndView.addObject("levels",levels);
        modelAndView.addObject("timeNow",timeNow);

        return modelAndView;
    }

    //随机找一个好友查看他的最近游戏
    private Map<String, Object> findPopularGames(List<FriendModel> friendModels){

        Map<String, Object> map = new HashMap<>(2);

        if (friendModels.size() > 0){
            boolean flag = true;
            int times = 0;
            while (flag){
                Random random = new Random();
                int index = random.nextInt(friendModels.size());
                FriendModel friendModel = friendModels.get(index);
                List<OwnedGame> popGames = ownedGameService.findRecentlyGame(friendModel.getFriendsteamid());
                times++;
                if (popGames.size() > 0 || times > 10){
                    System.out.printf("LUCKY DOG: "+friendModel.getPersonaname()+"\n");
                    map.put("luckyDog",friendModel);
                    map.put("popGames",popGames);
                    flag = false;
                }
            }
        }

        return map;
    }
}
