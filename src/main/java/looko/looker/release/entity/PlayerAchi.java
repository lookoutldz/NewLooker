package looko.looker.release.entity;

public class PlayerAchi extends PlayerAchiKey {
    private Integer achieved;

    private String description;

    private Integer unlocktime;

    public Integer getAchieved() {
        return achieved;
    }

    public void setAchieved(Integer achieved) {
        this.achieved = achieved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnlocktime() {
        return unlocktime;
    }

    public void setUnlocktime(Integer unlocktime) {
        this.unlocktime = unlocktime;
    }
}