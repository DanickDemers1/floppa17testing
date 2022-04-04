package ulaval.glo2003.entities.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductCategoryTest {
    @Test
    void givenValidEnumProductCategory_whenParsingToString_thenCanParseToValidValue() {
        var stringProductCategory = ProductCategory.SPORTS.enumToString();
        assertThat(stringProductCategory).isEqualTo("sports");
    }

    @Test
    void givenProductCategories_whenCallingToList_thenCanMakeProductCategoryList() {
        var productCategories = ProductCategory.toList();
        assertThat(productCategories).contains(ProductCategory.SPORTS);
        assertThat(productCategories).contains(ProductCategory.ELECTRONICS);
        assertThat(productCategories).contains(ProductCategory.APPAREL);
        assertThat(productCategories).contains(ProductCategory.BEAUTY);
        assertThat(productCategories).contains(ProductCategory.HOUSING);
        assertThat(productCategories).contains(ProductCategory.OTHER);
    }

    @Test
    void givenNullProductCategories_whenParsingStringListToProductCategoryList_thenReturnEmptyList() {
        var productCategories =
                ProductCategory.fromStringListToProductCategoryList(null);
        
        assertThat(productCategories).isEmpty();
    }

    @Test
    void givenValidStringList_whenParsingStringListToProductCategoryList_thenReturnProductCategoriesAsString() {
        var stringProductCategories = Arrays.asList(
                ProductCategory.APPAREL.enumToString(),
                ProductCategory.BEAUTY.enumToString());

        var productCategories = ProductCategory
                .fromStringListToProductCategoryList(stringProductCategories);

        assertThat(productCategories).contains(ProductCategory.APPAREL);
        assertThat(productCategories).contains(ProductCategory.BEAUTY);
    }

    @Test
    void givenNullStringProductCategories_whenParsingProductCategoryListToStringList_thenReturnEmptyList() {
        var productCategories = ProductCategory.fromProductCategoryListToStringList(null);
        assertThat(productCategories).isEmpty();
    }

    @Test
    void givenProductCategoryList_whenParsingProductCategoryListToStringList_thenReturnProductCategoriesAsString() {
        var productCategories = Arrays.asList(
                ProductCategory.APPAREL,
                ProductCategory.BEAUTY);

        var stringProductCategories = ProductCategory
                .fromProductCategoryListToStringList(productCategories);

        assertThat(stringProductCategories).contains(ProductCategory.APPAREL.enumToString());
        assertThat(stringProductCategories).contains(ProductCategory.BEAUTY.enumToString());
    }
}
