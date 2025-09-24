package dto.request;

public class TransactionRequestDTO {
    private String srcAddress;
    private String destAddress;
    private double amount;

    public TransactionRequestDTO(String srcAddress, String destAddress, double amount) {
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
        this.amount = amount;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
