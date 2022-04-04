package ulaval.glo2003.entities.seller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.api.utils.DateUtils;
import ulaval.glo2003.application.exceptions.InvalidParamException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;

public class SellerFactoryTest {
    SellerFactory sellerFactory;

    @BeforeEach
    void setUp() {
        sellerFactory = new SellerFactory();
    }

    @Test
    void givenValidSellerParameters_whenCreateSeller_thenSellerIsCreated() {
        Seller seller = sellerFactory.createSeller(
                SELLER_NAME,
                SELLER_BIO,
                SELLER_BIRTHDATE);

        assertThat(seller.getName()).isEqualTo(SELLER_NAME);
        assertThat(seller.getBio()).isEqualTo(SELLER_BIO);
        assertThat(seller.getBirthDate()).isEqualTo(DateUtils.parseStringToDate(SELLER_BIRTHDATE));
    }

    @Test
    void givenBlankSellerName_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(" ", SELLER_BIO, SELLER_BIRTHDATE));
    }

    @Test
    void givenBlankSellerBio_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, " ", SELLER_BIRTHDATE));
    }

    @Test
    void givenBlankSellerBirthDate_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, " "));
    }

    @Test
    void givenSellerAgeBelowEighteen_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "2018-12-10"));
    }

    @Test
    void givenInvalidSellerBirthDay_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "2018-12-99"));
    }

    @Test
    void givenInvalidSellerBirthMonth_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "2018-99-10"));
    }

    @Test
    void givenInvalidSellerBirthYear_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "9999-12-10"));
    }

    @Test
    void givenInvalidSellerBirthDate_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "not a number"));
    }

    @Test
    void givenIncompleteSellerBirthDate_whenCreateSeller_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, "99"));
    }

    @Test
    void givenValidSeller_whenCreateDuplicateSeller_thenTheirIdAreNotEqual() {
        Seller firstSeller = sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE);
        Seller duplicateSeller = sellerFactory.createSeller(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE);

        assertThat(firstSeller.getId()).isNotEqualTo(duplicateSeller.getId());
    }
}
