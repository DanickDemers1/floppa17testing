package ulaval.glo2003.entities.offer;

import ulaval.glo2003.entities.Entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Offer extends Entity {
    private final UUID id;
    private final OffsetDateTime createdAt;
    private UUID productId;
    private UUID buyerId;
    private float amount;
    private String message;

    public Offer(UUID productId, UUID buyerId, float amount, String message) {
        this.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        id = java.util.UUID.randomUUID();
        setProductId(productId);
        setBuyerId(buyerId);
        setAmount(amount);
        setMessage(message);
    }

    public Offer(UUID id, OffsetDateTime createdAt, UUID productId,
                 UUID buyerId, float amount, String message) {
        this.id = id;
        this.createdAt = createdAt;
        setProductId(productId);
        setBuyerId(buyerId);
        setAmount(amount);
        setMessage(message);
    }

    public UUID getId() { return id; }

    public UUID getProductId() { return productId; }

    public void setProductId(UUID productId) { this.productId = productId; }

    public UUID getBuyerId() { return buyerId; }

    public void setBuyerId(UUID buyerId) { this.buyerId = buyerId; }

    public float getAmount() { return amount; }

    public void setAmount(float amount) { this.amount = amount; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
