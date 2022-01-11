package view.tm;

public class PaymentReportsTM {
    private String stRegistrationNo;
    private String stName;
    private String paidDate;
    private String paidOrNot;

    public PaymentReportsTM() {
    }

    public PaymentReportsTM(String stRegistrationNo, String stName, String paidDate, String paidOrNot) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStName(stName);
        this.setPaidDate(paidDate);
        this.setPaidOrNot(paidOrNot);
    }

    public String getStRegistrationNo() {
        return stRegistrationNo;
    }

    public void setStRegistrationNo(String stRegistrationNo) {
        this.stRegistrationNo = stRegistrationNo;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getPaidOrNot() {
        return paidOrNot;
    }

    public void setPaidOrNot(String paidOrNot) {
        this.paidOrNot = paidOrNot;
    }
}
