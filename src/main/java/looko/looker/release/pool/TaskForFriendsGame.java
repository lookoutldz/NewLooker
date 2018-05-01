package looko.looker.release.pool;

import looko.looker.release.api.GetOwnedGame;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.service.DB_OwnedGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskForFriendsGame {

    @Autowired
    GetOwnedGame getOwnedGame;
    @Autowired
    DB_OwnedGameService oGameService;

    @Async("taskExecutor")
    public void go(String steamid) {
        List<OwnedGame> ownedGames = getOwnedGame.get(steamid);
        if (ownedGames.size() > 0){
            oGameService.updateOwnedGame(ownedGames);
        }
    }
}
