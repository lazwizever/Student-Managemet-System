package model;

public class Student {
    private String stRegisterNo;
    private String stName;
    private String stDob;
    private String stRegistrationDate;
    private String stGender;
    private String stGrade;
    private String stAddress;
    private String stContactNo;
    private ParentDetails parentDetails;

    public Student() {
    }

    public Student(String stRegisterNo, String stName, String stDob, String stRegistrationDate, String stGender, String stGrade, String stAddress, String stContactNo, ParentDetails parentDetails) {
        this.setStRegisterNo(stRegisterNo);
        this.setStName(stName);
        this.setStDob(stDob);
        this.setStRegistrationDate(stRegistrationDate);
        this.setStGender(stGender);
        this.setStGrade(stGrade);
        this.setStAddress(stAddress);
        this.setStContactNo(stContactNo);
        this.setParentDetails(parentDetails);
    }

    public String getStRegisterNo() {
        return stRegisterNo;
    }

    public void setStRegisterNo(String stRegisterNo) {
        this.stRegisterNo = stRegisterNo;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStDob() {
        return stDob;
    }

    public void setStDob(String stDob) {
        this.stDob = stDob;
    }

    public String getStRegistrationDate() {
        return stRegistrationDate;
    }

    public void setStRegistrationDate(String stRegistrationDate) {
        this.stRegistrationDate = stRegistrationDate;
    }

    public String getStGender() {
        return stGender;
    }

    public void setStGender(String stGender) {
        this.stGender = stGender;
    }

    public String getStGrade() {
        return stGrade;
    }

    public void setStGrade(String stGrade) {
        this.stGrade = stGrade;
    }

    public String getStAddress() {
        return stAddress;
    }

    public void setStAddress(String stAddress) {
        this.stAddress = stAddress;
    }

    public String getStContactNo() {
        return stContactNo;
    }

    public void setStContactNo(String stContactNo) {
        this.stContactNo = stContactNo;
    }

    public ParentDetails getParentDetails() {
        return parentDetails;
    }

    public void setParentDetails(ParentDetails parentDetails) {
        this.parentDetails = parentDetails;
    }
}
