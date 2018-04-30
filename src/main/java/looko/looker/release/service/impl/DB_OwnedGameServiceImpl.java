package looko.looker.release.service.impl;

import looko.looker.release.dao.OwnedGameMapper;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.OwnedGameKey;
import looko.looker.release.entity.OwnedGameKey2;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.tool.FindListsDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_OGameService")
public class DB_OwnedGameServiceImpl implements DB_OwnedGameService {

    @Autowired
    OwnedGameMapper ownedGameMapper;

    @Override
    public int updateOwnedGame(List<OwnedGame> ownedGames) {
        int row = 0;
        if (ownedGames.size() > 0){
            List<OwnedGame> oldlist = ownedGameMapper.selectByMyId(ownedGames.get(0).getSteamid());
            if (oldlist.size() > 0){
                List<List<OwnedGame>> re = FindListsDiff.checkOwnedGames(oldlist,ownedGames);
                List<OwnedGame> toAdd = re.get(0), toDel = re.get(1);
                if (toAdd.size() > 0){
                    for (OwnedGame ownedGame : toAdd){
                        row += ownedGameMapper.insert(ownedGame);
                    }
                }
                if (toDel.size() > 0){
                    OwnedGameKey prikey;
                    String steamid = ownedGames.get(0).getSteamid();
                    for (OwnedGame ownedGame : toDel){
                        prikey = new OwnedGameKey();
                        prikey.setSteamid(steamid);
                        prikey.setAppid(ownedGame.getAppid());
                        row += ownedGameMapper.deleteByPrimaryKey(prikey);
                    }
                }
            }
            else {
                for (OwnedGame ownedGame : ownedGames){
                    row += ownedGameMapper.insert(ownedGame);
                }
            }
        }
        return row;
    }

    @Override
    public OwnedGame findOwnedGameByPriKey(String steamid, int appid) {

        OwnedGameKey prikey = new OwnedGameKey();
        prikey.setSteamid(steamid);
        prikey.setAppid(appid);
        return ownedGameMapper.selectByPrimaryKey(prikey);
    }

    @Override
    public OwnedGame findGameBySteamidAppname(String steamid, String appname) {

        OwnedGameKey2 key = new OwnedGameKey2();
        key.setSteamid(steamid);
        key.setAppname(appname);
        return ownedGameMapper.selectBySteamidAppname(key);
    }

    @Override
    public List<OwnedGame> findOwnedGamesById(String steamid) {

        return ownedGameMapper.selectByMyId(steamid);
    }

    @Override
    public List<OwnedGame> findFavoriteById(String steamid) {

        return ownedGameMapper.selectByMyIdFavorite(steamid);
    }

    @Override
    public List<OwnedGame> findPlayedById(String steamid) {

        return ownedGameMapper.selectPlayed(steamid);
    }

    @Override
    public List<OwnedGame> findNotPlayedById(String steamid) {

        return ownedGameMapper.selectNotPlayed(steamid);
    }

    @Override
    public List<OwnedGame> findPerfectGame(String steamid) {

        return ownedGameMapper.selectPerfectGame(steamid);
    }

    @Override
    public int emptyOwnedGame() {
        return 0;
    }
}
