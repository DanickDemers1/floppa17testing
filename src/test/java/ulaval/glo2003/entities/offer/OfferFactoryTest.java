package ulaval.glo2003.entities.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.InvalidParamException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.product.Product;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;


@ExtendWith(MockitoExtension.class)
class OfferFactoryTest {
    Buyer buyer;
    Product product;
    OfferFactory offerFactory;

    @BeforeEach
    void setUp() {
        buyer = getDefaultBuyer();
        product = getDefaultProduct(getDefaultSeller().getId());
        offerFactory = new OfferFactory();
    }

    @Test
    void givenValidOfferInfo_whenCreateOffer_thenOfferIsCreated() {
        var offer = offerFactory.createOffer(product.getId(), buyer.getId(), OFFER_AMOUNT, OFFER_MESSAGE);

        assertThat(offer.getProductId()).isEqualTo(product.getId());
        assertThat(offer.getBuyerId()).isEqualTo(buyer.getId());
        assertThat(offer.getAmount()).isEqualTo(OFFER_AMOUNT);
        assertThat(offer.getMessage()).isEqualTo(OFFER_MESSAGE);
    }

    @Test
    void givenMessageOfLessThanAHundredCharacters_whenCreateOffer_thenThrowsWhenProductTitleIsBlank() {
        assertThrows(InvalidParamException.class,
                () -> offerFactory.createOffer(product.getId(), buyer.getId(), OFFER_AMOUNT, "message"));
    }
}
