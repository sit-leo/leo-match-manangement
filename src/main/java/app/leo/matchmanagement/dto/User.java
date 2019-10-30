package app.leo.matchmanagement.dto;

public class User {
    private long userId;
    private String role;
    private long profileId;

    public User() {
    }

    public User(long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public User(long userId, String role, long profileId) {
        this.userId = userId;
        this.role = role;
        this.profileId = profileId;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
