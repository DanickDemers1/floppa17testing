package ulaval.glo2003.entities.product;

import ulaval.glo2003.application.exceptions.InvalidParamException;

import java.util.List;
import java.util.UUID;

public class ProductFactory {
    public Product createProduct(String title, String description, Float suggestedPrice,
                                 List<ProductCategory> categories, UUID sellerId) {
        if (title.isBlank() || description.isBlank() || suggestedPrice < 1)
            throw new InvalidParamException();

        return new Product(title, description, suggestedPrice, categories, sellerId);
    }
}
