package looko.looker.release.entity;

public class Friend extends FriendKey {
    private String relationship;

    private Integer friendSince;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public Integer getFriendSince() {
        return friendSince;
    }

    public void setFriendSince(Integer friendSince) {
        this.friendSince = friendSince;
    }

    @Override
    public int hashCode() {
        return this.getFriendsteamid().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getFriendsteamid().equals(((Friend)obj).getFriendsteamid())&&this.getSteamid().equals(((Friend)obj).getSteamid());
    }
}