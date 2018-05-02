package looko.looker.release;

import looko.looker.release.api.GetGameSchema;
import looko.looker.release.api.GetOwnedGame;
import looko.looker.release.entity.AchiModel;
import looko.looker.release.entity.App;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.entity.Player;
import looko.looker.release.pool.TaskForAppInfo;
import looko.looker.release.pool.TaskForGameSchema;
import looko.looker.release.service.DB_AppService;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.service.DB_PlayerAchiService;
import looko.looker.release.service.DB_PlayerService;
import looko.looker.release.tool.FindListsDiff;
import looko.looker.release.tool.ResolveScreenshot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReleaseApplicationTests {

	@Autowired
	DB_AppService appService;
	@Autowired
	GetOwnedGame getOwnedGame;
	@Autowired
	GetGameSchema getGameSchema;

	@Autowired
	DB_PlayerService playerService;
	@Autowired
	DB_OwnedGameService ownedGameService;
	@Autowired
	DB_PlayerAchiService achiService;

	@Autowired
	TaskForAppInfo task;
	@Autowired
	TaskForGameSchema taskForGameSchema;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void contextLoads() {
		logger.warn("success");
	}

	@Test
	public void findBug_list(){

		List<OwnedGame> newGameList = getOwnedGame.get("76561198328486894");
//		List<OwnedGame> newGameList = getOwnedGame.get("76561198367830998");
		List<OwnedGame> oldGameList = ownedGameService.findOwnedGamesById("76561198367830998");
		List<List<OwnedGame>> re = FindListsDiff.checkOwnedGames(oldGameList, newGameList);
		List<OwnedGame> toAdd = re.get(0);
		List<OwnedGame> toDel = re.get(1);
		logger.info("待添加列表：");
		for (OwnedGame ownedGame : toAdd){
			logger.warn(ownedGame.getAppid() + " : " + ownedGame.getAppname());
		}
		logger.info("待删除列表：");
		for (OwnedGame ownedGame : toDel){
			logger.info(ownedGame.getAppid() + " : " + ownedGame.getAppname());
		}
	}

	@Test
	public void getAppInfo_test(){

		List<OwnedGame> ownedGames = ownedGameService.findFavoriteById("76561198367830998");
		logger.info("ownedgames.size = " + ownedGames.size());
		if (ownedGames.size() > 0){
			int i = 0;
			for (OwnedGame ownedGame : ownedGames){
				task.go(ownedGame.getAppid());
			}
		}
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void resolveInfo(){
		long time0 = System.currentTimeMillis();
		App app = appService.findAppById(637650);
		long time1 = System.currentTimeMillis();
		ResolveScreenshot.resolve(app);
		long time2 = System.currentTimeMillis();

		logger.info("查询数据"+(time1-time0)+"ms");
		logger.info("解析数据"+(time2-time1)+"ms");
	}

	@Test
	public void perfect(){

		List<OwnedGame> perfect_game = ownedGameService.findPerfectGame("76561198367830998");
		for (OwnedGame game : perfect_game){
			logger.info("appid="+game.getAppid()+"\tname : "+game.getAppname());
		}

	}

	@Test
	public void frinedAsPlayer(){

		List<Player> friendList = playerService.findFriendAsPlayer("76561198367830998");
		if (friendList.size() > 0){
			for (Player player : friendList){
				logger.info("steamid = " + player.getSteamid() + "\tname : " + player.getPersonaname());
			}
		}
	}

	@Test
	public void findAchiDetail(){
		List<AchiModel> achiModels = achiService.findMyAchiDetail("76561198367830998");
		logger.info("achisize = "+achiModels.size());
		if (achiModels.size() > 0){
			for (AchiModel model : achiModels){
				if (model == null)
					logger.warn("null");
				else
					logger.info("appname : "+model.getAppname()+"\tachiName : "+model.getDisplayName()+"\ticon : "+model.getIcon());
			}
		}
	}

	@Test
	public void getSchema(){
		getGameSchema.get(374320);
	}

}
