package looko.looker.release.tool;

import looko.looker.release.entity.App;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ResolveScreenshot {

    public static List<String> resolve(App app){

        List<String> list = new ArrayList<>();
        String str = app.getPicScreenshot();
        if (str != null){
            StringTokenizer st = new StringTokenizer(str,";");
            while (st.hasMoreTokens()){
                list.add(st.nextToken());
            }
        }
        return list;
    }

    public static List<String> resolve(String str){

        List<String> list = new ArrayList<>();
        if (str != null){
            StringTokenizer st = new StringTokenizer(str,";");
            while (st.hasMoreTokens()){
                list.add(st.nextToken());
            }
        }
        return list;
    }

    public static List<List<String>> resolve(List<String> stringList){

        List<List<String>> lists = new ArrayList<>();
        if (stringList.size() > 0){
            List<String> list;
            for (String str : stringList){
                list = new ArrayList<>();
                if (str != null){
                    StringTokenizer st = new StringTokenizer(str,";");
                    while (st.hasMoreTokens()){
                        list.add(st.nextToken());
                    }
                }
                lists.add(list);
            }
        }
        return lists;
    }
}
