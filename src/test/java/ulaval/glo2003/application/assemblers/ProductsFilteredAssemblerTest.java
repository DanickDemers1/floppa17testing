package ulaval.glo2003.application.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

@ExtendWith(MockitoExtension.class)
class ProductsFilteredAssemblerTest {
    Product firstProduct;
    Product secondProduct;
    Seller seller;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        firstProduct = getDefaultProduct(seller.getId());
        secondProduct = getDefaultProduct(seller.getId());
    }

    @Test
    void givenProductSellerAndOffers_whenToDto_thenReturnsProductDto() {
        var productDtos = new ArrayList<ProductDto>();
        var firstDto = ProductAssembler.toDto(firstProduct, seller, new ArrayList<>());
        var secondDto = ProductAssembler.toDto(secondProduct, seller, new ArrayList<>());
        productDtos.add(firstDto);
        productDtos.add(secondDto);

        var productsFilteredDto = ProductsFilteredAssembler.toDto(productDtos);

        assertThat(productsFilteredDto.products.size()).isEqualTo(productDtos.size());
        assertThat(productsFilteredDto.products).contains(firstDto);
        assertThat(productsFilteredDto.products).contains(secondDto);
    }

    @Test
    void givenNullProductDtoList_whenToDto_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class, () -> ProductsFilteredAssembler.toDto(null));
    }
}
