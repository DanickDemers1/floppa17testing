package ulaval.glo2003.infrastructure.persistence.assemblers;

import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.product.ProductCategory;
import ulaval.glo2003.infrastructure.persistence.models.ProductModel;
import ulaval.glo2003.infrastructure.persistence.models.SellerModel;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static ulaval.glo2003.entities.product.ProductCategory.fromStringListToProductCategoryList;

public class ProductDbAssembler {
    public static ProductModel toModel(Product product, SellerModel sellerModel) {
        var productModel = new ProductModel();
        productModel.id = product.getId();
        productModel.createdAt = product.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        productModel.title = product.getTitle();
        productModel.description = product.getDescription();
        productModel.suggestedPrice = product.getSuggestedPrice();

        productModel.categories = product.getCategories().stream()
                .map(ProductCategory::enumToString)
                .collect(Collectors.toList());

        productModel.sellerModel = sellerModel;

        return productModel;
    }

    public static Product toEntity(ProductModel productModel) {
        return new Product(
                productModel.id,
                productModel.title,
                productModel.description,
                productModel.suggestedPrice,
                OffsetDateTime.parse(productModel.createdAt),
                fromStringListToProductCategoryList(productModel.categories),
                productModel.sellerModel.id);
    }
}
