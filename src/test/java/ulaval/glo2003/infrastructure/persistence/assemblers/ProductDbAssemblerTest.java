package ulaval.glo2003.infrastructure.persistence.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.application.assemblers.SellerAssembler;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultProduct;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

class ProductDbAssemblerTest {
    Seller seller;
    Product product;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
    }

    @Test
    void givenProduct_whenSellerAssemblerToModel_thenReturnProductModel() {
        var productModel = ProductDbAssembler.toModel(product, SellerDbAssembler.toModel(seller));

        assertThat(productModel.id).isEqualTo(product.getId());
        assertThat(productModel.title).isEqualTo(product.getTitle());
        assertThat(productModel.description).isEqualTo(product.getDescription());
        assertThat(productModel.suggestedPrice).isEqualTo(product.getSuggestedPrice());
        assertThat(productModel.categories).isEqualTo(product.getCategoriesAsString());
        assertThat(productModel.sellerModel.id).isEqualTo(product.getSellerId());
    }

    @Test
    void givenProductModel_whenSellerAssemblerToEntity_thenReturnProduct() {
        var productModel = ProductDbAssembler.toModel(product, SellerDbAssembler.toModel(seller));
        var productEntity = ProductDbAssembler.toEntity(productModel);

        assertThat(productEntity.getId()).isEqualTo(product.getId());
        assertThat(productEntity.getTitle()).isEqualTo(product.getTitle());
        assertThat(productEntity.getDescription()).isEqualTo(product.getDescription());
        assertThat(productEntity.getSuggestedPrice()).isEqualTo(product.getSuggestedPrice());
        assertThat(productEntity.getCategories().size()).isEqualTo(product.getCategories().size());
        assertThat(productEntity.getSellerId()).isEqualTo(product.getSellerId());
    }
}