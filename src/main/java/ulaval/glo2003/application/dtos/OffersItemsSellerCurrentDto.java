package ulaval.glo2003.application.dtos;

public class OffersItemsSellerCurrentDto {
    public String id;
    public String createdAt;
    public float amount;
    public String message;
    public BuyerItemsSellerCurrentDto buyer;

    public OffersItemsSellerCurrentDto(String id, float amount, String message, String createdAt, BuyerItemsSellerCurrentDto buyer) {
        this.id = id;
        this.amount = amount;
        this.message = message;
        this.createdAt = createdAt;
        this.buyer = buyer;
    }
}
