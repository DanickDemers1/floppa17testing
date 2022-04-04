package ulaval.glo2003.infrastructure.persistence.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

class SellerRepositoryTest {
    static DbConnection dbConnection;
    static SellerRepository sellerRepository;
    Seller seller;

    @BeforeAll
    static void setSellerRepository() {
        dbConnection = new DbConnection(Environment.TEST);
        sellerRepository = new SellerRepository(dbConnection);
    }

    @BeforeEach
    void setup() {
        seller = getDefaultSeller();
    }

    @AfterEach
    void dropDatabase(){
        dbConnection.dropTestDatabase();
    }

    @Test
    void givenValidSeller_whenSaveSeller_thenSellerIsSaved() {
        sellerRepository.save(seller);
        var sellerFromDb = sellerRepository.get(seller.getId());

        assertThat(sellerFromDb.getId()).isEqualTo(seller.getId());
        assertThat(sellerFromDb.getName()).isEqualTo(seller.getName());
        assertThat(sellerFromDb.getBio()).isEqualTo(seller.getBio());
        assertThat(sellerFromDb.getCreatedAt()).isNotNull();
    }

    @Test
    void givenNonExistentSellerId_whenGetSeller_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> sellerRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedSeller_whenExistsCheckWithSellerId_thenReturnTrue() {
        sellerRepository.save(seller);

        var sellersExists = sellerRepository.exists(seller.getId());

        assertThat(sellersExists).isTrue();
    }

    @Test
    void givenNonExistentSeller_whenExistsCheckWithSellerId_thenReturnFalse() {
        var sellersExists = sellerRepository.exists(UUID.randomUUID());

        assertThat(sellersExists).isFalse();
    }

    @Test
    void givenANumberOfSavedSellers_whenGetAll_thenReturnValidListSize() {
        sellerRepository.save(getDefaultSeller());
        sellerRepository.save(getDefaultSeller());
        sellerRepository.save(getDefaultSeller());

        var allSellers = sellerRepository.getAll();

        assertThat(allSellers.size()).isEqualTo(3);
    }
}