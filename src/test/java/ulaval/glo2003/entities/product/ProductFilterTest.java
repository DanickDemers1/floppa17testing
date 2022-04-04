package ulaval.glo2003.entities.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

class ProductFilterTest {
    public ProductFilter productFilter;
    List<Product> productsList = new ArrayList<>();

    @Mock
    String mockedSellerIdFilter;
    String mockedTitleFilter;
    float mockedMinPriceFilter;
    float mockedMaxPriceFilter = -1.5f;
    List<ProductCategory> mockedCategoriesFilter;

    @BeforeEach
    void setUp() {

    }

    @Test
    void givenEmptyProductList_whenFilterProductsList_thenReturnsEmptyList(){
        productFilter = new ProductFilter(mockedSellerIdFilter, mockedTitleFilter,
                mockedMinPriceFilter, mockedMaxPriceFilter, mockedCategoriesFilter);
        List<Product> emptyProductsList = new ArrayList<>();

        List<Product> filteredProductsList = productFilter.filterProductsList(emptyProductsList);

        assertThat(filteredProductsList.size()).isEqualTo(0);
    }

    @Test
    void givenExistentTitleFilter_whenFilterProductsList_thenReturnValidListSize(){
        productFilter = new ProductFilter(mockedSellerIdFilter, TITLE_FILTER_IN,
                mockedMinPriceFilter,mockedMaxPriceFilter,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(2);
    }

    @Test
    void givenNonExistentTitleFilter_whenFilterProductsList_thenReturnEmptyList(){
        productFilter = new ProductFilter(mockedSellerIdFilter, TITLE_FILTER_OUT,
                mockedMinPriceFilter,mockedMaxPriceFilter,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(0);
    }

    @Test
    void givenHigherMaxPriceFilter_whenFilterProductsList_thenReturnValidListSize(){
        productFilter = new ProductFilter(mockedSellerIdFilter, mockedTitleFilter,
                mockedMinPriceFilter,HIGHER_PRICE,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(2);
    }

    @Test
    void givenLowerMaxPriceFilter_whenFilterProductsList_thenReturnEmptyList(){
        productFilter = new ProductFilter(mockedSellerIdFilter, mockedTitleFilter,
                mockedMinPriceFilter,LOWER_PRICE,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(0);
    }

    @Test
    void givenHigherMinPriceFilter_whenFilterProductsList_thenReturnEmptyList(){
        productFilter = new ProductFilter(mockedSellerIdFilter, mockedTitleFilter,
                HIGHER_PRICE, mockedMaxPriceFilter, mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(0);
    }

    @Test
    void givenLowerMinPriceFilter_whenFilterProductsList_thenReturnValidListSize(){
        productFilter = new ProductFilter(mockedSellerIdFilter, mockedTitleFilter,
                LOWER_PRICE, mockedMaxPriceFilter,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(2);
    }

    @Test
    void givenExistentSellerIdFilter_whenFilterProductsList_thenReturnValidListSize(){
        var firstSellerId = getDefaultSeller().getId();
        var secondSellerId = getDefaultSeller().getId();
        productFilter = new ProductFilter(firstSellerId.toString(), mockedTitleFilter,
                mockedMinPriceFilter, mockedMaxPriceFilter,mockedCategoriesFilter);
        productsList.add(getDefaultProduct(firstSellerId));
        productsList.add(getDefaultProduct(secondSellerId));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(1);
    }

    @Test
    void givenExistentCategoryFilter_whenFilterProductsList_thenReturnValidListSize(){
        productFilter = new ProductFilter(mockedSellerIdFilter,mockedTitleFilter,
                mockedMinPriceFilter,mockedMaxPriceFilter,CATEGORIES_FILTER_EXISTENT);
        Product product = new Product(PRODUCT_TITLE,PRODUCT_DESCRIPTION,PRODUCT_PRICE,
                CATEGORIES_FILTER_NON_EXISTENT,getDefaultSeller().getId());
        productsList.add(product);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        List<Product> filteredProductsList;

        filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(1);
    }

    @Test
    void givenNonExistentCategoryFilter_whenFilterProductsList_thenReturnEmptyList(){
        productFilter = new ProductFilter(mockedSellerIdFilter,mockedTitleFilter,
                mockedMinPriceFilter,mockedMaxPriceFilter,CATEGORIES_FILTER_NON_EXISTENT);
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        productsList.add(getDefaultProduct(getDefaultSeller().getId()));
        List<Product> filteredProductsList;

        filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(0);
    }

    @Test
    void givenExistentAllFilters_whenFilterProductsList_thenKeepDataIntegrity(){
        var sellerId = getDefaultSeller().getId();
        productFilter = new ProductFilter(sellerId.toString(), TITLE_FILTER_IN,
                LOWER_PRICE,HIGHER_PRICE,CATEGORIES_FILTER_EXISTENT);
        productsList.add(getDefaultProduct(sellerId));

        List<Product> filteredProductsList = productFilter.filterProductsList(productsList);

        assertThat(filteredProductsList.size()).isEqualTo(1);
        assertThat(filteredProductsList.get(0).getTitle()).isEqualTo(PRODUCT_TITLE);
        assertThat(filteredProductsList.get(0).getSuggestedPrice()).isEqualTo(PRODUCT_PRICE);
    }
}