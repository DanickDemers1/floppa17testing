package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.dtos.ProductShortDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
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
public class SellerAssemblerTest {
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
    void givenSeller_whenSellerAssemblerToDto_thenReturnSellerDto() {
        seller = getDefaultSeller();
        List<Product> sellerProducts = new ArrayList<>();
        var productsOffers = new HashMap<UUID, List<Offer>>();
        var sellerDto = SellerAssembler.toDto(seller, sellerProducts, productsOffers);

        assertThat(sellerDto.id).isEqualTo(seller.getId().toString());
        assertThat(sellerDto.name).isEqualTo(seller.getName());
        assertThat(sellerDto.bio).isEqualTo(seller.getBio());
        assertThat(sellerDto.createdAt).isNotNull();
        assertThat(sellerDto.products.size()).isEqualTo(0);
    }

    @Test
    void givenNullSeller_whenSellerAssemblerToDto_thenThrowNullReferenceException() {
        List<Product> sellerProducts = new ArrayList<>();
        HashMap<UUID, List<Offer>> sellerProductsOffers = new HashMap<>();

        assertThrows(NullReferenceException.class, () -> SellerAssembler.toDto(null, sellerProducts, sellerProductsOffers));
    }

    @Test
    void givenNullProducts_whenSellerAssemblerToDto_thenThrowNullReferenceException() {
        seller = getDefaultSeller();
        HashMap<UUID, List<Offer>> sellerProductsOffers = new HashMap<>();

        assertThrows(NullReferenceException.class, () -> SellerAssembler.toDto(seller, null, sellerProductsOffers));
    }

    @Test
    void givenNullProductsOffers_whenSellerAssemblerToDto_thenThrowNullReferenceException() {
        seller = getDefaultSeller();
        List<Product> sellerProducts = new ArrayList<>();

        assertThrows(NullReferenceException.class, () -> SellerAssembler.toDto(seller, sellerProducts, null));
    }

    @Test
    void givenSellerWithProducts_whenSellerAssemblerToDto_thenReturnProductDto() {
        product = getDefaultProduct(seller.getId());
        productShortDto = ProductShortAssembler.toDto(product, new ArrayList<>());
        var sellerProducts = new ArrayList<Product>();
        var productsOffers = new HashMap<UUID, List<Offer>>();
        sellerProducts.add(product);

        var sellerDto = SellerAssembler.toDto(seller, sellerProducts, productsOffers);

        assertThat(sellerDto.products).isNotEmpty();
        assertThat(sellerDto.products.get(0).description).isEqualTo(productShortDto.description);
        assertThat(sellerDto.products.get(0).title).isEqualTo(productShortDto.title);
        assertThat(sellerDto.products.get(0).suggestedPrice).isEqualTo(productShortDto.suggestedPrice);
    }
}
