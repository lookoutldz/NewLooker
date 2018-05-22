package looko.looker.release.entity;

public class GameRankModel {
    private String steamid;
    private Integer appid;
    private String appname;
    private String personaname;
    private String profileurl;
    private String avatar;
    private String avatarmedium;
    private String avatarfull;
    private Integer playtime2week;
    private Integer playtimeForever;

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

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
        this.appname = appname;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public Integer getPlaytime2week() {
        return playtime2week;
    }

    public void setPlaytime2week(Integer playtime2week) {
        this.playtime2week = playtime2week;
    }

    public Integer getplaytimeForever() {
        return playtimeForever;
    }

    public void setplaytimeForever(Integer playtimeForever) {
        this.playtimeForever = playtimeForever;
    }
}
