package model;

public class ReportTM {
    private String stRegistrationNo;
    private String stName;
    private String paymentType;
    private double amount;
    private double cash;
    private double balance;

    public ReportTM() {
    }

    public ReportTM(String stRegistrationNo, String stName, String paymentType, double amount, double cash, double balance) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStName(stName);
        this.setPaymentType(paymentType);
        this.setAmount(amount);
        this.setCash(cash);
        this.setBalance(balance);
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

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
