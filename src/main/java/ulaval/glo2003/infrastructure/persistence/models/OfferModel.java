package ulaval.glo2003.infrastructure.persistence.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;

import java.util.UUID;

@Entity("offers")
public class OfferModel {
    @Id
    public UUID id;
    public String createdAt;
    public float amount;
    public String message;

    @Reference
    public ProductModel productModel;
    @Reference
    public BuyerModel buyerModel;
}
