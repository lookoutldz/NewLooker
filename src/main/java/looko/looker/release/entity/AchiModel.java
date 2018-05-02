package looko.looker.release.entity;

public class AchiModel extends PlayerAchiKey {

    private String appname;
    private String imgIconUrl;
    private String imgLogoUrl;
    private String displayName;
    private String description;
    private Integer achieved;
    private Integer unlocktime;
    private Integer hidden;
    private String icon;
    private String icongray;
    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getImgIconUrl() {
        return imgIconUrl;
    }

    public void setImgIconUrl(String imgIconUrl) {
        this.imgIconUrl = imgIconUrl;
    }

    public String getImgLogoUrl() {
        return imgLogoUrl;
    }

    public void setImgLogoUrl(String imgLogoUrl) {
        this.imgLogoUrl = imgLogoUrl;
    }

    public Integer getAchieved() {
        return achieved;
    }

    public void setAchieved(Integer achieved) {
        this.achieved = achieved;
    }

    public Integer getUnlocktime() {
        return unlocktime;
    }

    public void setUnlocktime(Integer unlocktime) {
        this.unlocktime = unlocktime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcongray() {
        return icongray;
    }

    public void setIcongray(String icongray) {
        this.icongray = icongray;
    }
}
