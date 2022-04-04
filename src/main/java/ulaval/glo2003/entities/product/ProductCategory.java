package ulaval.glo2003.entities.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProductCategory {
    SPORTS,
    ELECTRONICS,
    APPAREL,
    BEAUTY,
    HOUSING,
    OTHER;

    public String enumToString() {
        return this.toString().toLowerCase();
    }

    public static List<ProductCategory> toList() {
        return Arrays.asList(ProductCategory.values());
    }

    public static List<ProductCategory> fromStringListToProductCategoryList(List<String> productCategories) {
        if (productCategories == null)
            return new ArrayList<>();

        var loweredProductCategories = productCategories.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return ProductCategory.toList()
                .stream()
                .filter(x -> loweredProductCategories.contains(x.enumToString()))
                .collect(Collectors.toList());
    }

    public static List<String> fromProductCategoryListToStringList(List<ProductCategory> productCategories) {
        if (productCategories == null)
            return new ArrayList<>();

        return productCategories.stream().map(ProductCategory::enumToString).collect(Collectors.toList());
    }
}

