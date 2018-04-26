package looko.looker.release.service.impl;

import looko.looker.release.dao.PlayerMapper;
import looko.looker.release.entity.Player;
import looko.looker.release.service.DB_PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DB_PlayerService")
public class DB_PlayerServiceImpl implements DB_PlayerService {

    @Autowired
    PlayerMapper playerMapper;

    @Override
    public int updatePlayer(Player player) {
        int row = 0;
        if (player != null){
            if (null != playerMapper.selectByPrimaryKey(player.getSteamid())){
                row += playerMapper.updateByPrimaryKey(player);
            }
            else{
                row += playerMapper.insert(player);
            }
        }
        return row;
    }

    @Override
    public int updateExtra(Player player) {
        int row = 0;
        if (null != playerMapper.selectByPrimaryKey(player.getSteamid())){
            row += playerMapper.updateByPrimaryKeySelective(player);
        }
        return row;
    }

    @Override
    public int checkVisiState(String steamid) {

        return playerMapper.checkVisibilityState(steamid);
    }

    @Override
    public Player findPlayerById(String steamid) {

        return playerMapper.selectByPrimaryKey(steamid);
    }

    @Override
    public List<Player> findPlayersByName(String name) {

        return playerMapper.selectByName("%"+name+"%");
    }

    @Override
    public int emptyPlayer() {
        return 0;
    }
}
