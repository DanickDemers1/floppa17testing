package ulaval.glo2003.infrastructure.persistence.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultProduct;
import static ulaval.glo2003.EntityBuilderTestUtils.getDefaultSeller;

class ProductRepositoryTest {
    static DbConnection dbConnection;
    static ProductRepository productRepository;
    static SellerRepository sellerRepository;
    static Seller seller;
    Product product;

    @BeforeAll
    static void setProductRepository() {
        dbConnection = new DbConnection(Environment.TEST);
        productRepository = new ProductRepository(dbConnection);
        sellerRepository = new SellerRepository(dbConnection);
        seller = getDefaultSeller();
    }

    @BeforeEach
    void setup() {
        sellerRepository.save(seller);
        product = getDefaultProduct(seller.getId());
    }

    @AfterEach
    void dropDatabase(){
        dbConnection.dropTestDatabase();
    }

    @Test
    void givenValidProduct_whenSaveProduct_thenProductIsSaved() {
        productRepository.save(product);
        var productFromDb = productRepository.get(product.getId());

        assertThat(productFromDb.getId()).isEqualTo(product.getId());
        assertThat(productFromDb.getTitle()).isEqualTo(product.getTitle());
        assertThat(productFromDb.getDescription()).isEqualTo(product.getDescription());
        assertThat(productFromDb.getSuggestedPrice()).isEqualTo(product.getSuggestedPrice());
        assertThat(productFromDb.getCategories().size()).isEqualTo(product.getCategories().size());
        assertThat(productFromDb.getSellerId()).isEqualTo(product.getSellerId());
    }

    @Test
    void givenNonExistentProductId_whenGetProduct_thenThrowsItemNotFoundException() {
        assertThrows(ItemNotFoundException.class,
                () -> productRepository.get(UUID.randomUUID()));
    }

    @Test
    void givenSavedProduct_whenExistsCheckWithProductId_thenReturnTrue() {
        productRepository.save(product);

        var productsExists = productRepository.exists(product.getId());

        assertThat(productsExists).isTrue();
    }

    @Test
    void givenNonExistentProduct_whenExistsCheckWithProductId_thenReturnFalse() {
        var productsExists = productRepository.exists(UUID.randomUUID());

        assertThat(productsExists).isFalse();
    }

    @Test
    void givenANumberOfSavedProducts_whenGetAll_thenReturnValidListSize() {
        productRepository.save(getDefaultProduct(seller.getId()));
        productRepository.save(getDefaultProduct(seller.getId()));
        productRepository.save(getDefaultProduct(seller.getId()));

        var allProducts = productRepository.getAll();

        assertThat(allProducts.size()).isEqualTo(3);
    }

}