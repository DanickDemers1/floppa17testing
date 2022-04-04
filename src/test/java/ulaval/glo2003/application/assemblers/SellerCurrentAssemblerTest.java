package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.dtos.ProductShortDto;
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
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultProduct;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

@ExtendWith(MockitoExtension.class)
public class SellerCurrentAssemblerTest {
    Seller seller;

    @Mock
    Product product;

    @Mock
    ProductShortDto productShortDto;


    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
    }

    @Test
    void givenCurrentSeller_whenToDto_thenReturnsSellerCurrentDto() {
        seller = getDefaultSeller();
        List<Product> sellerProducts = new ArrayList<>();
        var productsOffers = new HashMap<UUID, List<Offer>>();


        var sellerCurrentDto = SellerCurrentAssembler.toDto(seller, sellerProducts,productsOffers,new HashMap<UUID,Buyer>());

        assertThat(sellerCurrentDto.id).isEqualTo(seller.getId().toString());
        assertThat(sellerCurrentDto.name).isEqualTo(seller.getName());
        assertThat(sellerCurrentDto.bio).isEqualTo(seller.getBio());
        assertThat(sellerCurrentDto.createdAt).isNotNull();
        assertThat(sellerCurrentDto.products.size()).isEqualTo(0);
    }

    @Test
    void givenNullSeller_whenToDto_thenThrowsNullReferenceException() {
        HashMap<UUID, List<Offer>> sellerProductsOffers = new HashMap<>();
        assertThrows(NullReferenceException.class, () -> SellerCurrentAssembler.toDto(null, null,sellerProductsOffers,new HashMap<UUID,Buyer>()));
    }

    @Test
    void givenCurrentSeller_whenToDto_thenReturnsProducts() {
        product = getDefaultProduct(seller.getId());
        productShortDto = ProductShortAssembler.toDto(product, new ArrayList<>());
        var sellerProducts = new ArrayList<Product>();
        sellerProducts.add(product);
        var productsOffers = new HashMap<UUID, List<Offer>>();

        var sellerDto = SellerCurrentAssembler.toDto(seller, sellerProducts,productsOffers,new HashMap<UUID,Buyer>());

        assertThat(sellerDto.products).isNotEmpty();
        assertThat(sellerDto.products.get(0).description).isEqualTo(productShortDto.description);
        assertThat(sellerDto.products.get(0).title).isEqualTo(productShortDto.title);
        assertThat(sellerDto.products.get(0).suggestedPrice).isEqualTo(productShortDto.suggestedPrice);
    }
}
