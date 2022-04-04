package ulaval.glo2003.entities.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.entities.seller.Seller;

import static com.google.common.truth.Truth.assertThat;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    Product product;
    Seller seller;


    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
    }

    @Test
    void givenNullTitle_whenMatchTitle_thenReturnTrue() {
        var matchesTitle = product.matchesTitle(null);

        assertThat(matchesTitle).isEqualTo(true);
    }

    @Test
    void givenBlankTitle_whenMatchTitle_thenReturnTrue() {
        var matchesTitle = product.matchesTitle(null);

        assertThat(matchesTitle).isEqualTo(true);
    }

    @Test
    void givenStringThatIsPresentInProductTitle_whenMatchTitle_thenReturnTrue() {
        var matchesTitle = product.matchesTitle(PRODUCT_TITLE.substring(0, 6));

        assertThat(matchesTitle).isEqualTo(true);
    }

    @Test
    void givenNullSellerId_whenMatchSellerId_thenReturnTrue() {
        var matchesSellerId = product.matchesSellerId(null);

        assertThat(matchesSellerId).isEqualTo(true);
    }

    @Test
    void givenBlankSellerId_whenMatchSellerId_thenReturnFalse() {
        var matchesSellerId = product.matchesSellerId(" ");

        assertThat(matchesSellerId).isEqualTo(false);
    }

    @Test
    void givenExistentSellerId_whenMatchSellerId_thenReturnTrue() {
        var matchesSellerId = product.matchesSellerId(product.getSellerId().toString());

        assertThat(matchesSellerId).isEqualTo(true);
    }

    @Test
    void givenLowerMinPrice_whenMatchMinPrice_thenReturnTrue() {
        var lowerPrice = product.getSuggestedPrice() - 1;

        var matchesMinPrice = product.matchesMinPrice(lowerPrice);

        assertThat(matchesMinPrice).isEqualTo(true);
    }

    @Test
    void givenHigherMinPrice_whenMatchMinPrice_thenReturnFalse() {
        var higherPrice = product.getSuggestedPrice() + 1;

        var matchesMinPrice = product.matchesMinPrice(higherPrice);

        assertThat(matchesMinPrice).isEqualTo(false);
    }

    @Test
    void givenLowerMaxPrice_whenMatchMaxPrice_thenReturnFalse() {
        var lowerPrice = product.getSuggestedPrice() - 1;

        var matchesMaxPrice = product.matchesMaxPrice(lowerPrice);

        assertThat(matchesMaxPrice).isEqualTo(false);
    }

    @Test
    void givenHigherMaxPrice_whenMatchMaxPrice_thenReturnTrue() {
        var higherPrice = product.getSuggestedPrice() + 1;

        var matchesMaxPrice = product.matchesMaxPrice(higherPrice);

        assertThat(matchesMaxPrice).isEqualTo(true);
    }

    @Test
    void givenNullProductCategory_whenMatchCategories_thenReturnTrue() {
        var matchesCategories = product.matchesCategories(null);

        assertThat(matchesCategories).isEqualTo(true);
    }

    @Test
    void givenExistentProductCategory_whenMatchCategories_thenReturnTrue() {
        var matchesCategories = product.matchesCategories(CATEGORIES_FILTER_EXISTENT);

        assertThat(matchesCategories).isEqualTo(true);
    }

    @Test
    void givenNonExistentProductCategory_whenMatchCategories_thenReturnFalse() {
        var matchesCategories = product.matchesCategories(CATEGORIES_FILTER_NON_EXISTENT);

        assertThat(matchesCategories).isEqualTo(false);
    }

}
