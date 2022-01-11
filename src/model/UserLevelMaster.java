package model;

public class UserLevelMaster {
    private String userLevelId;
    private String userLevel;

    public UserLevelMaster() {
    }

    public UserLevelMaster(String userLevelId, String userLevel) {
        this.setUserLevelId(userLevelId);
        this.setUserLevel(userLevel);
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
