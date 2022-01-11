package model;

public class PaymentMaster {
    private String paymentId;
    private String paymentType;
    private double amount;

    public PaymentMaster() {
    }

    public PaymentMaster(String paymentId, String paymentType, double amount) {
        this.setPaymentId(paymentId);
        this.setPaymentType(paymentType);
        this.setAmount(amount);
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
