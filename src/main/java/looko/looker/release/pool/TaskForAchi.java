package looko.looker.release.pool;

import looko.looker.release.api.GetPlayerAchi;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.PlayerAchi;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.tool.ApplicationContextHelper;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class TaskForAchi extends Thread{

    private GetPlayerAchi getPlayerAchi = ApplicationContextHelper.getBean(GetPlayerAchi.class);
    private DB_PlayerAchiService achiService = ApplicationContextHelper.getBean(DB_PlayerAchiService.class);

    private String steamid;
    private OwnedGame ownedGame;

    public TaskForAchi(String steamid, OwnedGame ownedGame){
        this.steamid = steamid;
        this.ownedGame = ownedGame;
    }

    @Override
    @Async("executor")
    public void run() {
        List<PlayerAchi> playerAchis = getPlayerAchi.get(steamid,ownedGame.getAppid());
        if (playerAchis.size() > 0){
            achiService.updatePlayerAchi(playerAchis);
        }
    }
}
