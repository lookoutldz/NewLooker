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
}
