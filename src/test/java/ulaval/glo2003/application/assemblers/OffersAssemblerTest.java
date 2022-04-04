package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.api.utils.DoubleUtils;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultOffer;

@ExtendWith(MockitoExtension.class)
class OffersAssemblerTest {
    List<Offer> offers;

    @BeforeEach
    void setUp() {
        offers = new ArrayList<>();
        var buyer = getDefaultBuyer();
        var product = getDefaultProduct(getDefaultSeller().getId());
        offers.add(getDefaultOffer(product.getId(), buyer.getId()));
        offers.add(getDefaultOffer(product.getId(), buyer.getId()));
        offers.add(getDefaultOffer(product.getId(), buyer.getId()));
    }

    @Test
    void givenOffers_whenToDto_thenReturnsOffersDto() {
        var offersDto = OffersAssembler.toDto(offers);

        assertThat(offersDto.count).isEqualTo(offers.size());
        assertThat(offersDto.mean).isEqualTo(DoubleUtils.roundToTwoDecimals(OFFER_AMOUNT));
    }

    @Test
    void givenNullOffer_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> OffersAssembler.toDto(null));
    }
}
