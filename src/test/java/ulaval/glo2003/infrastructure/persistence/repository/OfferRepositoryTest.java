package ulaval.glo2003.infrastructure.persistence.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

class OfferRepositoryTest {
    static DbConnection dbConnection;
    static SellerRepository sellerRepository;
    static ProductRepository productRepository;
    static BuyerRepository buyerRepository;
    static OfferRepository offerRepository;
    static Seller seller;
    static Buyer buyer;
    static Product product;
    Offer offer;


    @BeforeAll
    static void setOfferRepository() {
        dbConnection = new DbConnection(Environment.TEST);

        sellerRepository = new SellerRepository(dbConnection);
        productRepository = new ProductRepository(dbConnection);
        buyerRepository = new BuyerRepository(dbConnection);
        offerRepository = new OfferRepository(dbConnection);

        seller = getDefaultSeller();
        product = getDefaultProduct(seller.getId());
        buyer = getDefaultBuyer();
    }

    @BeforeEach
    void setup() {
        sellerRepository.save(seller);
        productRepository.save(product);
        buyerRepository.save(buyer);
        offer = getDefaultOffer(product.getId(), buyer.getId());
    }

    @AfterEach
    void dropDatabase(){
        dbConnection.dropTestDatabase();
    }

    @Test
    void givenValidOffer_whenSaveOffer_thenOfferIsSaved() {
        offerRepository.save(offer);
        var offerFromDb = offerRepository.get(offer.getId());

        assertThat(offerFromDb.getId()).isEqualTo(offer.getId());
        assertThat(offerFromDb.getAmount()).isEqualTo(offer.getAmount());
        assertThat(offerFromDb.getMessage()).isEqualTo(offer.getMessage());
        assertThat(offerFromDb.getBuyerId()).isEqualTo(offer.getBuyerId());
        assertThat(offerFromDb.getProductId()).isEqualTo(offer.getProductId());
    }

    @Test
    void givenNonExistentOfferId_whenGetOffer_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> offerRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedOffer_whenExistsCheckWithOfferId_thenReturnTrue() {
        offerRepository.save(offer);

        var offersExists = offerRepository.exists(offer.getId());

        assertThat(offersExists).isTrue();
    }

    @Test
    void givenNonExistentOffer_whenExistsCheckWithOfferId_thenReturnFalse() {
        var offersExists = offerRepository.exists(UUID.randomUUID());

        assertThat(offersExists).isFalse();
    }

    @Test
    void givenANumberOfSavedOffers_whenGetAll_thenReturnValidListSize() {
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));

        var allOffers = offerRepository.getAll();

        assertThat(allOffers.size()).isEqualTo(3);
    }

    @Test
    void givenANumberOfSavedOffers_whenGetOffersForProduct_thenReturnValidListSize() {
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));
        offerRepository.save(getDefaultOffer(product.getId(), buyer.getId()));
        offerRepository.save(getDefaultOffer(getDefaultProduct(seller.getId()).getId(), buyer.getId()));

        var allOffersForProduct = offerRepository.getOffersForProduct(product.getId());

        assertThat(allOffersForProduct.size()).isEqualTo(3);
    }
}