package model;

public class Payment {
    private String paymentId;
    private String stRegistrationNo;
    private String paymentTypeId;
    private String paymentDate;

    public Payment() {
    }

    public Payment(String paymentId, String stRegistrationNo, String paymentTypeId, String paymentDate) {
        this.setPaymentId(paymentId);
        this.setStRegistrationNo(stRegistrationNo);
        this.setPaymentTypeId(paymentTypeId);
        this.setPaymentDate(paymentDate);
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStRegistrationNo() {
        return stRegistrationNo;
    }

    public void setStRegistrationNo(String stRegistrationNo) {
        this.stRegistrationNo = stRegistrationNo;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
