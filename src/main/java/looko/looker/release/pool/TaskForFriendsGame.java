package looko.looker.release.pool;

import looko.looker.release.api.GetOwnedGame;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.tool.ApplicationContextHelper;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class TaskForFriendsGame extends Thread {

    private GetOwnedGame getOwnedGame = ApplicationContextHelper.getBean(GetOwnedGame.class);
    private DB_OwnedGameService oGameService = ApplicationContextHelper.getBean(DB_OwnedGameService.class);

    private String steamid;

    public TaskForFriendsGame(String steamid){
        this.steamid = steamid;
    }

    @Override
    @Async("executor")
    public void run() {
        List<OwnedGame> ownedGames = getOwnedGame.get(steamid);
        if (ownedGames.size() > 0){
            oGameService.updateOwnedGame(ownedGames);
        }
    }
}
