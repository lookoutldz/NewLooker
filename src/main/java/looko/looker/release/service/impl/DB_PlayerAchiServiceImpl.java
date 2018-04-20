package looko.looker.release.service.impl;

import looko.looker.release.dao.PlayerAchiMapper;
import looko.looker.release.entity.PlayerAchi;
import looko.looker.release.entity.PlayerAchiGameKey;
import looko.looker.release.entity.PlayerAchiKey;
import looko.looker.release.service.DB_PlayerAchiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_PlayerAchiService")
public class DB_PlayerAchiServiceImpl implements DB_PlayerAchiService {

    @Autowired
    PlayerAchiMapper playerAchiMapper;

    @Override
    public int updatePlayerAchi(List<PlayerAchi> playerAchis) {
        int row = 0;
        if (playerAchis.size() > 0){
            String steamid = playerAchis.get(0).getSteamid();
            PlayerAchiKey prikey;
            for (PlayerAchi playerAchi : playerAchis){
                prikey = new PlayerAchiKey();
                prikey.setSteamid(steamid);
                prikey.setAchname(playerAchi.getAchname());
                prikey.setAppid(playerAchi.getAppid());
                if (null != playerAchiMapper.selectByPrimaryKey(prikey)){
                    row += playerAchiMapper.updateByPrimaryKey(playerAchi);
                }
                else{
                    row += playerAchiMapper.insert(playerAchi);
                }
            }
        }
        return 0;
    }

    @Override
    public List<PlayerAchi> findAllAchis(String steamid) {

        return playerAchiMapper.selectAllAchi(steamid);
    }

    @Override
    public List<PlayerAchi> findAchieved(String steamid) {

        return playerAchiMapper.selectAchieved(steamid);
    }

    @Override
    public List<PlayerAchi> findNotAchieve(String steamid) {

        return playerAchiMapper.selectNotAchieve(steamid);
    }

    @Override
    public List<PlayerAchi> findAllAchisByGame(String steamid, int appid) {

        PlayerAchiGameKey key = new PlayerAchiGameKey();
        key.setSteamid(steamid);
        key.setAppid(appid);
        return playerAchiMapper.selectAllAchiByGame(key);
    }

    @Override
    public List<PlayerAchi> findAchievedByGame(String steamid, int appid) {
        PlayerAchiGameKey key = new PlayerAchiGameKey();
        key.setSteamid(steamid);
        key.setAppid(appid);
        return playerAchiMapper.selectAchievedByGame(key);
    }

    @Override
    public List<PlayerAchi> findNotAchieveByGame(String steamid, int appid) {

        PlayerAchiGameKey key = new PlayerAchiGameKey();
        key.setSteamid(steamid);
        key.setAppid(appid);
        return playerAchiMapper.selectNotAchieveByGame(key);
    }

    @Override
    public int countAllAchis(String steamid) {

        return playerAchiMapper.countAllAchi(steamid);
    }

    @Override
    public int countAchieved(String steamid) {

        return playerAchiMapper.countAchieved(steamid);
    }

    @Override
    public int countAllAchisByGame(String steamid, int appid) {

        PlayerAchiGameKey key = new PlayerAchiGameKey();
        key.setSteamid(steamid);
        key.setAppid(appid);
        return playerAchiMapper.countAllAchiByGame(key);
    }

    @Override
    public int countAchievedByGame(String steamid, int appid) {

        PlayerAchiGameKey key = new PlayerAchiGameKey();
        key.setSteamid(steamid);
        key.setAppid(appid);
        return playerAchiMapper.countAchievedByGame(key);
    }

    @Override
    public int countGameHasAchi(String steamid) {

        return playerAchiMapper.countGameHasAchi(steamid);
    }

    @Override
    public int countPerfectGame(String steamid) {

        return playerAchiMapper.countPerfectGame(steamid);
    }

    @Override
    public List<Integer> findPerfectGameId(String steamid) {

        return playerAchiMapper.selectPerfectAppids(steamid);
    }

    @Override
    public int emptyPlayerAchi() {
        return 0;
    }
}
