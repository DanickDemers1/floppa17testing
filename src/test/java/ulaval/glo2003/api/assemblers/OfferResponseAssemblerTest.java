package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

@ExtendWith(MockitoExtension.class)
public class OfferResponseAssemblerTest {
    Offer offer;
    Buyer buyer;
    Product product;

    @BeforeEach
    void setUp() {
        buyer = getDefaultBuyer();
        product = getDefaultProduct(getDefaultSeller().getId());
        offer = getDefaultOffer(product.getId(), buyer.getId());
    }

    @Test
    void givenValidOffer_whenToCreateOfferResponse_thenResponseHasCreatedStatus() {
        var createOfferResponse = OfferResponseAssembler.toCreateOfferResponse(offer);

        assertThat(createOfferResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void givenValidOffer_whenToCreateOfferResponse_thenResponseLocation() {
        var createOfferResponse = OfferResponseAssembler.toCreateOfferResponse(offer);

        var expectedPath = ControllerPaths.PRODUCTS + "/" + product.getId() + "/offers/" + offer.getId();
        assertThat(createOfferResponse.getLocation().toString()).isEqualTo(expectedPath);
    }

    @Test
    void givenNullOffer_whenToCreateOfferResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> OfferResponseAssembler.toCreateOfferResponse(null));
    }
}
