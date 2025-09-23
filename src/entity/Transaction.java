package entity;

import entity.enums.FeePriority;
import entity.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String id;
    private String srcAddress;
    private String destAddress;
    private double amount;
    private double fee;
    private FeePriority feePriority;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private int mempoolPosition;

    public Transaction(String sourceAddress, String destinationAddress, double amount,
                       FeePriority feePriority, double fees) {
        this.id = UUID.randomUUID().toString();
        this.srcAddress = sourceAddress;
        this.destAddress = destinationAddress;
        this.amount = amount;
        this.feePriority = feePriority;
        this.fee = fees;
        this.status = TransactionStatus.PENDING;
        this.timestamp = LocalDateTime.now();
        this.mempoolPosition = -1;
    }


    public int getMempoolPosition() {
        return mempoolPosition;
    }

    public void setMempoolPosition(int mempoolPosition) {
        this.mempoolPosition = mempoolPosition;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public FeePriority getFeePriority() {
        return feePriority;
    }

    public void setFeePriority(FeePriority feePriority) {
        this.feePriority = feePriority;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public void setSrcAddress(String srcAddress) {
        this.srcAddress = srcAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("TX[%s]: From %s -> %s | Amount: %.2f | Fees: %.2f | Priority: %s | Status: %s",
                id.substring(0, 8), srcAddress, destAddress, amount, fee, feePriority, status);
    }

}
