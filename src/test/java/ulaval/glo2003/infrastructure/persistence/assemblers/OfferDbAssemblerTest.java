package ulaval.glo2003.infrastructure.persistence.assemblers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

class OfferDbAssemblerTest {
    Seller seller;
    Product product;
    Offer offer;
    Buyer buyer;

    @BeforeEach
    void setUp() {
        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
        buyer = getDefaultBuyer();
        offer = getDefaultOffer(product.getId(), buyer.getId());
    }

    @Test
    void givenOffer_whenSellerAssemblerToModel_thenReturnOfferModel() {
        var offerModel = OfferDbAssembler.toModel(offer,
                ProductDbAssembler.toModel(product, SellerDbAssembler.toModel(seller)),
                BuyerDbAssembler.toModel(buyer));

        assertThat(offerModel.id).isEqualTo(offer.getId());
        assertThat(offerModel.amount).isEqualTo(offer.getAmount());
        assertThat(offerModel.message).isEqualTo(offer.getMessage());
        assertThat(offerModel.buyerModel.id).isEqualTo(buyer.getId());
        assertThat(offerModel.productModel.id).isEqualTo(product.getId());
    }

    @Test
    void givenProductOffer_whenSellerAssemblerToEntity_thenReturnOffer() {
        var offerModel = OfferDbAssembler.toModel(offer,
                ProductDbAssembler.toModel(product, SellerDbAssembler.toModel(seller)),
                BuyerDbAssembler.toModel(buyer));

        var offerEntity = OfferDbAssembler.toEntity(offerModel);

        assertThat(offerEntity.getId()).isEqualTo(offer.getId());
        assertThat(offerEntity.getAmount()).isEqualTo(offer.getAmount());
        assertThat(offerEntity.getMessage()).isEqualTo(offer.getMessage());
        assertThat(offerEntity.getBuyerId()).isEqualTo(offer.getBuyerId());
        assertThat(offerEntity.getProductId()).isEqualTo(offer.getProductId());
    }
}