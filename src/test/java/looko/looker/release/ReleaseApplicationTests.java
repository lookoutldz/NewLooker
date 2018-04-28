package looko.looker.release;

import looko.looker.release.api.GetOwnedGame;
import looko.looker.release.entity.OwnedGame;
import looko.looker.release.pool.TaskForAppInfo;
import looko.looker.release.service.DB_OwnedGameService;
import looko.looker.release.tool.FindListsDiff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReleaseApplicationTests {

	@Autowired
	GetOwnedGame getOwnedGame;

	@Autowired
	DB_OwnedGameService ownedGameService;

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
		TaskForAppInfo task = new TaskForAppInfo(637650);
		task.start();
		try {
			task.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
