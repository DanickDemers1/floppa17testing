package ulaval.glo2003.entities.product;

import java.util.ArrayList;
import java.util.List;

public class ProductFilter {
    private final String sellerIdFilter;
    private final String titleFilter;
    private final float minPriceFilter;
    private final float maxPriceFilter;
    private final List<ProductCategory> categoriesFilter;

    public ProductFilter(String sellerIdFilter, String titleFilter,
                         float minPriceFilter, float maxPriceFilter, List<ProductCategory> categoriesFilter){
        this.sellerIdFilter = sellerIdFilter;
        this.titleFilter = titleFilter;
        this.minPriceFilter = minPriceFilter;
        this.maxPriceFilter= maxPriceFilter;
        this.categoriesFilter = categoriesFilter;
    }

    public List<Product> filterProductsList(List<Product> productsList){
        List<Product> filteredProductList = new ArrayList<>();
        for (Product product: productsList) {
            if (!product.matchesTitle(titleFilter))
                continue;
            if (!product.matchesSellerId(sellerIdFilter))
                continue;
            if (!product.matchesMaxPrice(maxPriceFilter))
                continue;
            if (!product.matchesMinPrice(minPriceFilter))
                continue;
            if (!product.matchesCategories(categoriesFilter))
                continue;

            filteredProductList.add(product);
        }
        return filteredProductList;
    }
}
