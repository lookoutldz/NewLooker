package looko.looker.release.entity;

public class PlayerAchi extends PlayerAchiKey {
    private Integer achieved;

    private Integer unlocktime;

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
}