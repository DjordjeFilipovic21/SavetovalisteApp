package app.model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private Client client;
    private LocalDate paymentDate;
    private double amount;
    private String currencyInitial;
    private String paymentMethod;
    private boolean firstInstallment;
    private String purpose;

    public Payment() {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyInitial() {
        return currencyInitial;
    }

    public void setCurrencyInitial(String currencyInitial) {
        this.currencyInitial = currencyInitial;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isFirstInstallment() {
        return firstInstallment;
    }

    public void setFirstInstallment(boolean firstInstallment) {
        this.firstInstallment = firstInstallment;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
