package ulaval.glo2003.api.requests;

import ulaval.glo2003.entities.product.ProductCategory;

import java.util.List;

public class CreateProductRequest implements Request {
    public String title;
    public String description;
    public Float suggestedPrice;
    public List<String> categories;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String title, String description, Float suggestedPrice, List<String> categories) {
        this.title = title;
        this.description = description;
        this.suggestedPrice = suggestedPrice;
        this.categories = categories;
    }

    public Boolean allParametersAreDefined() {
        return title != null && description != null && suggestedPrice != null;
    }

    public List<ProductCategory> getProductCategories() {
        return ProductCategory.fromStringListToProductCategoryList(categories);
    }
}
