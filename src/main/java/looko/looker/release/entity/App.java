package looko.looker.release.entity;

public class App {
    private Integer appid;

    private String appname;

    private String chname;

    private Integer price;

    private String imgIconUrl;

    private String imgLogoUrl;

    private String picLogobar;

    private String picScreenshot;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname == null ? null : chname.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImgIconUrl() {
        return imgIconUrl;
    }

    public void setImgIconUrl(String imgIconUrl) {
        this.imgIconUrl = imgIconUrl == null ? null : imgIconUrl.trim();
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl == null ? null : imgLogoUrl.trim();
    }

    public String getPicLogobar() {
        return picLogobar;
    }

    public void setPicLogobar(String picLogobar) {
        this.picLogobar = picLogobar == null ? null : picLogobar.trim();
    }

    public String getPicScreenshot() {
        return picScreenshot;
    }

    public void setPicScreenshot(String picScreenshot) {
        this.picScreenshot = picScreenshot == null ? null : picScreenshot.trim();
    }
}