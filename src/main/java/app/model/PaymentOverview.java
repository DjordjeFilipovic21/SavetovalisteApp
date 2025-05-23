package app.model;

public class PaymentOverview {
    private String clientName;
    private double totalPaid;
    private boolean hasPendingSecondInstallment;

    public PaymentOverview(String clientName, double totalPaid, boolean hasPendingSecondInstallment) {
        this.clientName = clientName;
        this.totalPaid = totalPaid;
        this.hasPendingSecondInstallment = hasPendingSecondInstallment;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public boolean isHasPendingSecondInstallment() {
        return hasPendingSecondInstallment;
    }

    public void setHasPendingSecondInstallment(boolean hasPendingSecondInstallment) {
        this.hasPendingSecondInstallment = hasPendingSecondInstallment;
    }
}
