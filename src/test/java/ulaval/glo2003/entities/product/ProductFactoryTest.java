package ulaval.glo2003.entities.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.InvalidParamException;
import ulaval.glo2003.entities.seller.Seller;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;


@ExtendWith(MockitoExtension.class)
class ProductFactoryTest {
    ProductFactory productFactory;
    Seller seller;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        productFactory = new ProductFactory();
    }

    @Test
    void givenValidProductParameters_whenCreateProduct_thenProductIsCreated() {
        Product product = productFactory.createProduct(
                PRODUCT_TITLE,
                PRODUCT_DESCRIPTION,
                PRODUCT_PRICE,
                PRODUCT_CATEGORIES,
                seller.getId());

        assertThat(product.getTitle()).isEqualTo(PRODUCT_TITLE);
        assertThat(product.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(product.getSuggestedPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getCategories()).isEqualTo(PRODUCT_CATEGORIES);
        assertThat(product.getSellerId()).isEqualTo(seller.getId());
    }

    @Test
    void givenBlankProductTitle_whenCreateProduct_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> productFactory.createProduct("", PRODUCT_DESCRIPTION, PRODUCT_PRICE,
                        PRODUCT_CATEGORIES, seller.getId()));
    }

    @Test
    void givenBlankProductDescription_whenCreateProduct_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> productFactory.createProduct(PRODUCT_TITLE, "", PRODUCT_PRICE,
                        PRODUCT_CATEGORIES, seller.getId()));
    }

    @Test
    void givenSuggestedPriceLowerThanOne_whenCreateProduct_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> productFactory.createProduct(PRODUCT_TITLE, PRODUCT_DESCRIPTION, 0.99f,
                        PRODUCT_CATEGORIES, seller.getId()));
    }
}
