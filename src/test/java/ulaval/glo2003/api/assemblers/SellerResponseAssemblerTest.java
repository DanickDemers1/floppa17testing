package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.assemblers.SellerAssembler;
import ulaval.glo2003.application.dtos.SellerDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

@ExtendWith(MockitoExtension.class)
public class SellerResponseAssemblerTest {
    SellerDto sellerDto;

    @Mock
    Seller seller;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        var sellerInMemoryRepository = new InMemoryRepository<Seller>();
        sellerInMemoryRepository.save(seller);
    }

    @Test
    void givenValidSeller_whenToCreateSellerResponse_thenResponseHasCreatedStatusAndSellerIdInLocation() {
        Response createSellerResponse = SellerResponseAssembler.toCreateSellerResponse(seller);

        assertThat(createSellerResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        assertThat(createSellerResponse.getLocation().toString())
                .isEqualTo(ControllerPaths.SELLERS + "/" + seller.getId());
    }

    @Test
    void givenNullSeller_whenToCreateSellerResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> SellerResponseAssembler.toCreateSellerResponse(null));
    }

    @Test
    void givenValidSeller_whenToGetSellerResponse_thenResponseHasOkStatus() {
        List<Product> sellerProducts = new ArrayList<>();
        var productsOffers = new HashMap<UUID, List<Offer>>();
        sellerDto = SellerAssembler.toDto(seller, sellerProducts, productsOffers);

        Response getSellerResponse = SellerResponseAssembler.toGetSellerResponse(sellerDto);

        assertThat(getSellerResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    void givenNullSeller_whenToGetSellerResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> SellerResponseAssembler.toGetSellerResponse(null));
    }
}
