package dto.response;

import entity.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionResponseDTO {
    private UUID id;
    private String srcAddress;
    private String destAddress;
    private double amount;
    private double fee;
    private TransactionStatus status;
    private LocalDateTime timestamp;

    public TransactionResponseDTO(UUID id, String srcAddress, String destAddress, double amount, double fee, TransactionStatus status, LocalDateTime timestamp) {
        this.id = id;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
        this.amount = amount;
        this.fee = fee;
        this.status = status;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getSrcAddress() {
        return srcAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public double getAmount() {
        return amount;
    }

    public double getFee() {
        return fee;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
