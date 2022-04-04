package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.assemblers.ProductAssembler;
import ulaval.glo2003.application.assemblers.ProductsFilteredAssembler;
import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.dtos.ProductsFilteredDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.util.ArrayList;

import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProductResponseAssemblerTest {
    Seller seller;
    Product product;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
    }

    @Test
    void givenValidProduct_whenToCreateProductResponse_thenResponseHasCreatedStatus() {
        Response createProductResponse = ProductResponseAssembler.toCreateProductResponse(product);

        assertThat(createProductResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }

    @Test
    void givenValidProduct_whenToCreateProductResponse_thenResponseLocation() {
        Response createProductResponse = ProductResponseAssembler.toCreateProductResponse(product);

        assertThat(createProductResponse.getLocation().toString())
                .isEqualTo(ControllerPaths.PRODUCTS + "/" + product.getId());
    }

    @Test
    void givenNullProduct_whenToCreateProductResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> ProductResponseAssembler.toCreateProductResponse(null));
    }

    @Test
    void givenValidProduct_whenToGetProductResponse_thenResponseHasOkStatus() {
        var productDto = ProductAssembler.toDto(product, seller, new ArrayList<>());

        Response createProductResponse = ProductResponseAssembler.toGetProductResponse(productDto);

        assertThat(createProductResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    void givenValidProduct_whenToGetProductResponse_thenResponseContainsProduct() {
        var productDto = ProductAssembler.toDto(product, seller, new ArrayList<>());

        Response createProductResponse = ProductResponseAssembler.toGetProductResponse(productDto);

        assertThat(((ProductDto) createProductResponse.getEntity()).id).isEqualTo(product.getId().toString());
    }

    @Test
    void givenNullProduct_whenToGetProductResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> ProductResponseAssembler.toGetProductResponse(null));
    }

    @Test
    void givenValidProduct_whenToGetFilteredProductsResponse_thenResponseHasOkStatus() {
        var productsDto = new ArrayList<ProductDto>();
        productsDto.add(ProductAssembler.toDto(product, seller, new ArrayList<>()));
        var productsFilteredDto = ProductsFilteredAssembler.toDto(productsDto);

        Response createProductResponse = ProductResponseAssembler.toGetFilteredProductsResponse(productsFilteredDto);

        assertThat(createProductResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    @Test
    void givenValidProduct_whenToGetFilteredProductsResponse_thenResponseContainsProduct() {
        var productsDto = new ArrayList<ProductDto>();
        productsDto.add(ProductAssembler.toDto(product, seller, new ArrayList<>()));
        var productsFilteredDto = ProductsFilteredAssembler.toDto(productsDto);

        Response createProductResponse = ProductResponseAssembler.toGetFilteredProductsResponse(productsFilteredDto);

        var entity = ((ProductsFilteredDto) createProductResponse.getEntity());
        assertThat(entity.products.size()).isEqualTo(productsFilteredDto.products.size());
    }

    @Test
    void givenNullProduct_whenToGetFilteredProductsResponse_thenThrowsNullReferenceException() {
        assertThrows(NullReferenceException.class,
                () -> ProductResponseAssembler.toGetFilteredProductsResponse(null));
    }
}
