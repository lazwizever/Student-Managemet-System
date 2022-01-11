package model;

public class ParentDetails {
    private String prtId;
    private String stRegistrationNo;
    private String prtName;
    private String prtTitle;
    private String prtGender;
    private String prtJob;
    private String prtNIC;
    private String prtAddress;
    private String prtEmail;
    private String prtContactNo;

    public ParentDetails() {
    }

    public ParentDetails(String prtId, String stRegistrationNo, String prtName, String prtTitle, String prtGender, String prtJob, String prtNIC, String prtAddress, String prtEmail, String prtContactNo) {
        this.setPrtId(prtId);
        this.setStRegistrationNo(stRegistrationNo);
        this.setPrtName(prtName);
        this.setPrtTitle(prtTitle);
        this.setPrtGender(prtGender);
        this.setPrtJob(prtJob);
        this.setPrtNIC(prtNIC);
        this.setPrtAddress(prtAddress);
        this.setPrtEmail(prtEmail);
        this.setPrtContactNo(prtContactNo);
    }

    public String getPrtId() {
        return prtId;
    }

    public void setPrtId(String prtId) {
        this.prtId = prtId;
    }

    public String getStRegistrationNo() {
        return stRegistrationNo;
    }

    public void setStRegistrationNo(String stRegistrationNo) {
        this.stRegistrationNo = stRegistrationNo;
    }

    public String getPrtName() {
        return prtName;
    }

    public void setPrtName(String prtName) {
        this.prtName = prtName;
    }

    public String getPrtTitle() {
        return prtTitle;
    }

    public void setPrtTitle(String prtTitle) {
        this.prtTitle = prtTitle;
    }

    public String getPrtGender() {
        return prtGender;
    }

    public void setPrtGender(String prtGender) {
        this.prtGender = prtGender;
    }

    public String getPrtJob() {
        return prtJob;
    }

    public void setPrtJob(String prtJob) {
        this.prtJob = prtJob;
    }

    public String getPrtNIC() {
        return prtNIC;
    }

    public void setPrtNIC(String prtNIC) {
        this.prtNIC = prtNIC;
    }

    public String getPrtAddress() {
        return prtAddress;
    }

    public void setPrtAddress(String prtAddress) {
        this.prtAddress = prtAddress;
    }

    public String getPrtEmail() {
        return prtEmail;
    }

    public void setPrtEmail(String prtEmail) {
        this.prtEmail = prtEmail;
    }

    public String getPrtContactNo() {
        return prtContactNo;
    }

    public void setPrtContactNo(String prtContactNo) {
        this.prtContactNo = prtContactNo;
    }
}
