package ulaval.glo2003.infrastructure.persistence.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.seller.Seller;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultBuyer;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

class BuyerDbAssemblerTest {
    Buyer buyer;

    @BeforeEach
    void setUp() {
        buyer = getDefaultBuyer();
    }

    @Test
    void givenBuyer_whenBuyerAssemblerToModel_thenReturnBuyerModel() {
        var buyerModel = BuyerDbAssembler.toModel(buyer);

        assertThat(buyerModel.id).isEqualTo(buyer.getId());
        assertThat(buyerModel.name).isEqualTo(buyer.getName());
        assertThat(buyerModel.email).isEqualTo(buyer.getEmail());
        assertThat(buyerModel.phoneNumber).isEqualTo(buyer.getPhoneNumber());
    }

    @Test
    void givenBuyerModel_whenSellerAssemblerToEntity_thenReturnBuyer() {
        var buyerModel = BuyerDbAssembler.toModel(buyer);
        var buyerEntity = BuyerDbAssembler.toEntity(buyerModel);

        assertThat(buyerEntity.getId()).isEqualTo(buyer.getId());
        assertThat(buyerEntity.getName()).isEqualTo(buyer.getName());
        assertThat(buyerEntity.getEmail()).isEqualTo(buyer.getEmail());
        assertThat(buyerEntity.getPhoneNumber()).isEqualTo(buyer.getPhoneNumber());
    }
}