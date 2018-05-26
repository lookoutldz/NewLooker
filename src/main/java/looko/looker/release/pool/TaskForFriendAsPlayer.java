package looko.looker.release.pool;

import looko.looker.release.api.GetPlayerSummaries;
import looko.looker.release.api.GetSteamLevel;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskForFriendAsPlayer {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GetPlayerSummaries getPlayer;
    @Autowired
    GetSteamLevel getSteamLevel;
    @Autowired
    DB_PlayerService playerService;

    @Async("taskExecutor")
    public void go(String friendsteamid) {

        Player player = getPlayer.get(friendsteamid);
        player.setSteamlevel(getSteamLevel.get(friendsteamid));
        playerService.updatePlayer(player);
    }
}
