package ulaval.glo2003.infrastructure.persistence.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.application.assemblers.SellerAssembler;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

class SellerDbAssemblerTest {
    Seller seller;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
    }

    @Test
    void givenSeller_whenSellerAssemblerToModel_thenReturnSellerModel() {
        var sellerModel = SellerDbAssembler.toModel(seller);

        assertThat(sellerModel.id).isEqualTo(seller.getId());
        assertThat(sellerModel.name).isEqualTo(seller.getName());
        assertThat(sellerModel.bio).isEqualTo(seller.getBio());
        assertThat(sellerModel.createdAt).isNotNull();
    }

    @Test
    void givenSellerModel_whenSellerAssemblerToEntity_thenReturnSeller() {
        var sellerModel = SellerDbAssembler.toModel(seller);
        var sellerEntity = SellerDbAssembler.toEntity(sellerModel);

        assertThat(sellerEntity.getId()).isEqualTo(seller.getId());
        assertThat(sellerEntity.getName()).isEqualTo(seller.getName());
        assertThat(sellerEntity.getBio()).isEqualTo(seller.getBio());
        assertThat(sellerEntity.getCreatedAt()).isNotNull();
    }
}