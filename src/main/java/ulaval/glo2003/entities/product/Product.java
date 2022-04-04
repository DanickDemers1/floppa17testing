package ulaval.glo2003.entities.product;

import ulaval.glo2003.entities.Entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Product extends Entity {
    private final UUID id;
    private final UUID sellerId;
    private final String title;
    private final String description;
    private final float suggestedPrice;
    private final OffsetDateTime createdAt;
    public List<ProductCategory> categories;

    public Product(String title, String description, float suggestedPrice,
                   List<ProductCategory> categories, UUID sellerId) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.suggestedPrice = suggestedPrice;
        this.createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        this.categories = categories;
        this.sellerId = sellerId;
    }

    public Product(UUID id, String title, String description,
                   float suggestedPrice, OffsetDateTime createdAt,
                   List<ProductCategory> categories, UUID sellerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.suggestedPrice = suggestedPrice;
        this.createdAt = createdAt;
        this.categories = categories;
        this.sellerId = sellerId;
    }

    public UUID getId() { return id; }

    public UUID getSellerId() { return sellerId; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getSuggestedPrice() {
        return suggestedPrice;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public List<String> getCategoriesAsString() {
        return categories.stream().map(ProductCategory::enumToString).collect(Collectors.toList());
    }

    public Boolean matchesTitle(String title) {
        if (title == null)
            return true;

        return this.title.toLowerCase().contains(title.toLowerCase());
    }

    public Boolean matchesSellerId(String sellerId) {
        if (sellerId == null)
            return true;

        if (sellerId.isBlank())
            return false;

        return this.sellerId.toString().equals(sellerId.toLowerCase());
    }

    public Boolean matchesMinPrice(float minPrice) {
        if (minPrice < 0)
            return true;

        return this.suggestedPrice >= minPrice;
    }

    public Boolean matchesMaxPrice(float maxPrice) {
        if (maxPrice < 0)
            return true;

        return this.suggestedPrice <= maxPrice;
    }

    public Boolean matchesCategories(List<ProductCategory> categories){
        if (categories == null || categories.isEmpty())
            return true;

        for (ProductCategory category : categories)
            if (this.categories.contains(category))
                return true;

        return false;
    }
}
