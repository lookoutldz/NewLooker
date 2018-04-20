package looko.looker.release.entity;

public class OwnedGame extends OwnedGameKey {
    private String appname;

    private Integer playtime2week;

    private Integer playtimeForever;

    private String imgIconUrl;

    private String imgLogoUrl;

    private Boolean hasCommunityVisibleState;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public Integer getPlaytime2week() {
        return playtime2week;
    }

    public void setPlaytime2week(Integer playtime2week) {
        this.playtime2week = playtime2week;
    }

    public Integer getPlaytimeForever() {
        return playtimeForever;
    }

    public void setPlaytimeForever(Integer playtimeForever) {
        this.playtimeForever = playtimeForever;
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

    public Boolean getHasCommunityVisibleState() {
        return hasCommunityVisibleState;
    }

    public void setHasCommunityVisibleState(Boolean hasCommunityVisibleState) {
        this.hasCommunityVisibleState = hasCommunityVisibleState;
    }

    @Override
    public int hashCode() {
        return this.getAppid().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getAppid()==((OwnedGame)obj).getAppid()&&this.getSteamid().equals(((OwnedGame)obj).getSteamid());
    }
}