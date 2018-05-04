package looko.looker.release.tool;

import looko.looker.release.entity.OwnedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountHoursGames {

    public static List<Integer> findByList(List<OwnedGame> ownedGames){

        List<Integer> list = new ArrayList<>(11);
        int h2 = 0, h10 = 0, h50 = 0, h100 = 0, h200 = 0, h500 = 0, h1000 = 0, h2000 = 0, h5000 = 0, h10000 = 0, haha = 0;
        if (ownedGames.size() > 0){
            for (OwnedGame ownedGame : ownedGames){
                int playtime = ownedGame.getPlaytimeForever();
                if (playtime < 120){
                    h2++;
                }
                else if (playtime < 600){
                    h10++;
                }
                else if (playtime < 3000){
                    h50++;
                }
                else if (playtime < 6000){
                    h100++;
                }
                else if (playtime < 12000){
                    h200++;
                }
                else if (playtime < 30000){
                    h500++;
                }
                else if (playtime < 600000){
                    h1000++;
                }
                else if (playtime < 1200000){
                    h2000++;
                }
                else if (playtime < 3000000){
                    h5000++;
                }
                else if (playtime < 6000000){
                    h10000++;
                }
                else {
                    haha++;
                }
            }
        }
        list.add(h2);
        list.add(h10);
        list.add(h50);
        list.add(h100);
        list.add(h200);
        list.add(h500);
        list.add(h1000);
        list.add(h2000);
        list.add(h5000);
        list.add(h10000);
        list.add(haha);
//        showlist(list);
        return list;
    }

    private static void showlist(List<Integer> list){
        System.out.printf("2小时以内的游戏：" + list.get(0) + "\n");
        System.out.printf("2~10小时的游戏：" + list.get(1) + "\n");
        System.out.printf("10~50小时的游戏：" + list.get(2) + "\n");
        System.out.printf("50~100小时的游戏：" + list.get(3) + "\n");
        System.out.printf("100~200小时的游戏：" + list.get(4) + "\n");
        System.out.printf("200~500小时的游戏：" + list.get(5) + "\n");
        System.out.printf("500~1000小时的游戏：" + list.get(6) + "\n");
        System.out.printf("1000~2000小时的游戏：" + list.get(7) + "\n");
        System.out.printf("2000~5000小时的游戏：" + list.get(8) + "\n");
        System.out.printf("5000~10000小时的游戏：" + list.get(9) + "\n");
        System.out.printf("10000小时以上的游戏：" + list.get(10) + "\n");
    }
}
