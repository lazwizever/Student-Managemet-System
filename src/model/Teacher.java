package model;

public class Teacher {
    private String teacherId;
    private String teacherName;
    private String userLevelId;
    private String userName;
    private String passWord;

    public Teacher() {
    }

    public Teacher(String teacherId, String teacherName, String userLevelId, String userName, String passWord) {
        this.setTeacherId(teacherId);
        this.setTeacherName(teacherName);
        this.setUserLevelId(userLevelId);
        this.setUserName(userName);
        this.setPassWord(passWord);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
