package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

@ExtendWith(MockitoExtension.class)
class ProductShortAssemblerTest {
    Product product;
    Seller seller;
    Buyer buyer;
    Offer offer;

    @BeforeEach
    void setUp() {
        buyer = getDefaultBuyer();
        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
        offer = getDefaultOffer(product.getId(), buyer.getId());
    }

    @Test
    void givenProduct_whenToDto_thenReturnsProductShortDto() {
        var productOffers = new ArrayList<Offer>();
        productOffers.add(offer);

        var productShortDto = ProductShortAssembler.toDto(product, productOffers);

        assertThat(productShortDto.id).isEqualTo(product.getId().toString());
        assertThat(productShortDto.title).isEqualTo(product.getTitle());
        assertThat(productShortDto.description).isEqualTo(product.getDescription());
        assertThat(productShortDto.suggestedPrice).isEqualTo(product.getSuggestedPrice());
        assertThat(productShortDto.categories).isEqualTo(product.getCategoriesAsString());
        assertThat(productShortDto.offers.count).isEqualTo(productOffers.size());
    }

    @Test
    void givenNullProduct_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductShortAssembler.toDto(null, new ArrayList<>()));
    }

    @Test
    void givenNullOffersList_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductShortAssembler.toDto(product, null));
    }
}
