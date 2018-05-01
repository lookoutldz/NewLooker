package looko.looker.release.pool;

import looko.looker.release.api.GetPlayerAchi;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.PlayerAchi;
import looko.looker.release.service.DB_PlayerAchiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskForAchi{

    @Autowired
    GetPlayerAchi getPlayerAchi;
    @Autowired
    DB_PlayerAchiService achiService;

    @Async("taskExecutor")
    public void go(String steamid, OwnedGame ownedGame) {
        List<PlayerAchi> playerAchis = getPlayerAchi.get(steamid,ownedGame.getAppid());
        if (playerAchis.size() > 0){
            achiService.updatePlayerAchi(playerAchis);
        }
    }
}
