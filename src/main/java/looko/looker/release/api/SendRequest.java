package looko.looker.release.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class SendRequest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected InputStream sendGet(String url_str){
        try
        {
            URL url = new URL(url_str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            int responseCode = con.getResponseCode();
            if (con.getResponseCode() != 200){
                logger.info("WebSite = "+url_str + "\nresponseCode = "+responseCode + "\treturn InputStream is null");
                return null;
            }
            InputStream is = con.getInputStream();
            return is;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
