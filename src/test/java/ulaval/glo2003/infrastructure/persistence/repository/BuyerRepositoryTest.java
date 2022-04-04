package ulaval.glo2003.infrastructure.persistence.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultBuyer;


class BuyerRepositoryTest {
    static DbConnection dbConnection;
    static BuyerRepository buyerRepository;
    Buyer buyer;

    @BeforeAll
    static void setBuyerRepository() {
        dbConnection = new DbConnection(Environment.TEST);
        buyerRepository = new BuyerRepository(dbConnection);
    }

    @BeforeEach
    void setup() {
        buyer = getDefaultBuyer();
    }

    @AfterEach
    void dropDatabase(){
        dbConnection.dropTestDatabase();
    }

    @Test
    void givenValidBuyer_whenSaveBuyer_thenBuyerIsSaved() {
        buyerRepository.save(buyer);
        var buyerFromDb = buyerRepository.get(buyer.getId());

        assertThat(buyerFromDb.getId()).isEqualTo(buyer.getId());
        assertThat(buyerFromDb.getName()).isEqualTo(buyer.getName());
        assertThat(buyerFromDb.getEmail()).isEqualTo(buyer.getEmail());
        assertThat(buyerFromDb.getPhoneNumber()).isEqualTo(buyer.getPhoneNumber());
    }

    @Test
    void givenNonExistentBuyerId_whenGetBuyer_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> buyerRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedBuyer_whenExistsCheckWithBuyerId_thenReturnTrue() {
        buyerRepository.save(buyer);

        var buyersExists = buyerRepository.exists(buyer.getId());

        assertThat(buyersExists).isTrue();
    }

    @Test
    void givenNonExistentBuyer_whenExistsCheckWithBuyerId_thenReturnFalse() {
        var buyersExists = buyerRepository.exists(UUID.randomUUID());

        assertThat(buyersExists).isFalse();
    }

    @Test
    void givenANumberOfSavedBuyers_whenGetAll_thenReturnValidListSize() {
        buyerRepository.save(getDefaultBuyer());
        buyerRepository.save(getDefaultBuyer());
        buyerRepository.save(getDefaultBuyer());

        var allBuyers = buyerRepository.getAll();

        assertThat(allBuyers.size()).isEqualTo(3);
    }
}