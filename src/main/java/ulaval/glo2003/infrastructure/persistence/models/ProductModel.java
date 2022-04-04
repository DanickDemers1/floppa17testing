package ulaval.glo2003.infrastructure.persistence.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;

import java.util.List;
import java.util.UUID;

@Entity("products")
public  class ProductModel {
    @Id
    public UUID id;
    public String title;
    public String description;
    public float suggestedPrice;
    public String createdAt;
    public List<String> categories;

    @Reference
    public  SellerModel sellerModel;
}
