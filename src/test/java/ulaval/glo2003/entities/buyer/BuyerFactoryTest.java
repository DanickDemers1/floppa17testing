package ulaval.glo2003.entities.buyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.application.exceptions.InvalidParamException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ulaval.glo2003.EntityBuilderTestUtils.*;


@ExtendWith(MockitoExtension.class)
class BuyerFactoryTest {
    BuyerFactory buyerFactory;

    @BeforeEach
    void setUp() {
        buyerFactory = new BuyerFactory();
    }

    @Test
    void givenValidBuyerInfo_whenCreateBuyer_thenBuyerIsCreated() {
        var buyer = buyerFactory.createBuyer(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE);

        assertThat(buyer.getName()).isEqualTo(BUYER_NAME);
        assertThat(buyer.getEmail()).isEqualTo(BUYER_EMAIL);
        assertThat(buyer.getPhoneNumber()).isEqualTo(BUYER_PHONE);
    }

    @Test
    void givenBlankName_whenCreateBuyer_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> buyerFactory.createBuyer("", BUYER_EMAIL, BUYER_PHONE));
    }

    @Test
    void givenInvalidPhone_whenCreateBuyer_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> buyerFactory.createBuyer(BUYER_NAME, BUYER_EMAIL, "123456789"));
    }

    @Test
    void givenInvalidEmail_whenCreateBuyer_thenThrowsInvalidParamException() {
        assertThrows(InvalidParamException.class,
                () -> buyerFactory.createBuyer(BUYER_NAME, "123@quatre", BUYER_PHONE));
    }
}
