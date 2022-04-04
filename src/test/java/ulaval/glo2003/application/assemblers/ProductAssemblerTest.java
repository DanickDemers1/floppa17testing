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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static ulaval.glo2003.api.utils.DoubleUtils.roundToTwoDecimals;

@ExtendWith(MockitoExtension.class)
class ProductAssemblerTest {
    Product product;
    Seller seller;
    Buyer buyer;
    Offer offer;

    @BeforeEach
    void setUp() {
        product = getDefaultProduct(getDefaultSeller().getId());
        seller = getDefaultSeller();
        buyer = getDefaultBuyer();
        offer = getDefaultOffer(product.getId(), buyer.getId());
    }

    @Test
    void givenProductSellerAndOffers_whenToDto_thenReturnsProductDto() {
        var sellerProducts = new ArrayList<Product>();
        var productOffers = new ArrayList<Offer>();
        var productsOffers = new HashMap<UUID, List<Offer>>();
        sellerProducts.add(product);
        productOffers.add(offer);
        productsOffers.put(product.getId(), productOffers);

        var productDto = ProductAssembler.toDto(product, seller, productOffers);

        assertThat(productDto.id).isEqualTo(product.getId().toString());
        assertThat(productDto.title).isEqualTo(product.getTitle());
        assertThat(productDto.description).isEqualTo(product.getDescription());
        assertThat(productDto.suggestedPrice).isEqualTo(product.getSuggestedPrice());
        assertThat(productDto.categories).isEqualTo(product.getCategoriesAsString());
        assertThat(productDto.seller.id).isEqualTo(SellerAssembler.toDto(seller, sellerProducts, productsOffers).id);
        assertThat(productDto.offers.count).isEqualTo(productOffers.size());
        assertThat(productDto.offers.mean).isEqualTo(roundToTwoDecimals(productOffers.stream().mapToDouble(Offer::getAmount).average().getAsDouble()));
    }

    @Test
    void givenNullProduct_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductAssembler.toDto(null, seller, new ArrayList<>()));
    }

    @Test
    void givenNullSeller_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductAssembler.toDto(product, null, new ArrayList<>()));
    }

    @Test
    void givenNullOffersList_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductAssembler.toDto(product, seller, null));
    }
}
