package ulaval.glo2003.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.repository.InMemoryRepository;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

@ExtendWith(MockitoExtension.class)
class InMemoryRepositoryTest {
    InMemoryRepository<Product> productInMemoryRepository;
    InMemoryRepository<Seller> sellerInMemoryRepository;

    @BeforeEach
    void setUp() {
        productInMemoryRepository = new InMemoryRepository<>();
        sellerInMemoryRepository = new InMemoryRepository<>();
    }

    @Test
    void givenValidProduct_whenSaveProduct_thenProductIsSaved() {
        var product = getDefaultProduct(getDefaultSeller().getId());

        productInMemoryRepository.save(product);

        var fetchedProduct = productInMemoryRepository.get(product.getId());
        assertThat(fetchedProduct.getId()).isEqualTo(product.getId());
        assertThat(fetchedProduct.getTitle()).isEqualTo(product.getTitle());
        assertThat(fetchedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(fetchedProduct.getCreatedAt()).isEqualTo(product.getCreatedAt());
        assertThat(fetchedProduct.getSuggestedPrice()).isEqualTo(product.getSuggestedPrice());
        assertThat(fetchedProduct.getCategories()).isEqualTo(product.getCategories());
    }

    @Test
    void givenNonExistentProductId_whenGetProduct_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> productInMemoryRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedProduct_whenRemoveProduct_thenProductIsRemoved() {
        var product = getDefaultProduct(getDefaultSeller().getId());
        productInMemoryRepository.save(product);

        productInMemoryRepository.remove(product.getId());

        assertThrows(ItemNotFoundException.class,
                () -> productInMemoryRepository.get(product.getId()));
    }

    @Test
    void givenValidSeller_whenSaveSeller_thenSellerIsSaved() {
        var seller = getDefaultSeller();

        sellerInMemoryRepository.save(seller);

        var fetchedSeller = sellerInMemoryRepository.get(seller.getId());
        assertThat(fetchedSeller.getId()).isEqualTo(seller.getId());
        assertThat(fetchedSeller.getName()).isEqualTo(seller.getName());
        assertThat(fetchedSeller.getBio()).isEqualTo(seller.getBio());
        assertThat(fetchedSeller.getCreatedAt()).isEqualTo(seller.getCreatedAt());
        assertThat(fetchedSeller.getBirthDate()).isEqualTo(seller.getBirthDate());
    }

    @Test
    void givenNonExistentSellerId_whenGetSeller_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> sellerInMemoryRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedSeller_whenRemoveSeller_thenSellerIsRemoved() {
        var seller = getDefaultSeller();
        sellerInMemoryRepository.save(seller);

        sellerInMemoryRepository.remove(seller.getId());

        assertThrows(ItemNotFoundException.class,
                () -> sellerInMemoryRepository.get(seller.getId()));
    }

    @Test
    void givenSavedSeller_whenGettingSavedProductWithSellerId_thenProductIsAddedToSeller() {
        var seller = getDefaultSeller();
        sellerInMemoryRepository.save(seller);
        var product = getDefaultProduct(seller.getId());
        productInMemoryRepository.save(product);

        var dbProduct = productInMemoryRepository.get(product.getId());

        assertThat(dbProduct).isNotNull();
        assertThat(dbProduct.getSellerId()).isEqualTo(seller.getId());
    }

    @Test
    void givenSavedSeller_whenExistsCheckWithSellerId_thenReturnTrue() {
        var seller = getDefaultSeller();
        sellerInMemoryRepository.save(seller);

        var sellerExists = sellerInMemoryRepository.exists(seller.getId());

        assertThat(sellerExists).isTrue();
    }

    @Test
    void givenNonExistentSeller_whenExistsCheckWithSellerId_thenReturnFalse() {
        var sellerExists = sellerInMemoryRepository.exists(UUID.randomUUID());

        assertThat(sellerExists).isFalse();
    }

    @Test
    void givenANumberOfSavedSellers_whenGetAll_thenReturnValidListSize() {
        sellerInMemoryRepository.save(getDefaultSeller());
        sellerInMemoryRepository.save(getDefaultSeller());
        sellerInMemoryRepository.save(getDefaultSeller());

        var allSellers = sellerInMemoryRepository.getAll();

        assertThat(allSellers.size()).isEqualTo(3);
    }
}
