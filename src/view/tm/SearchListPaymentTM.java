package view.tm;

public class SearchListPaymentTM {
    private String stRegistrationNo;
    private String stName;
    private String paymentId;
    private String paymentType;

    public SearchListPaymentTM() {
    }

    public SearchListPaymentTM(String stRegistrationNo, String stName, String paymentId, String paymentType) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStName(stName);
        this.setPaymentId(paymentId);
        this.setPaymentType(paymentType);
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
