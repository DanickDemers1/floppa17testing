package ulaval.glo2003.api;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.Environment;
import ulaval.glo2003.Main;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.io.IOException;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static spark.Spark.stop;
import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static ulaval.glo2003.api.End2EndTestUtils.*;
import static ulaval.glo2003.api.ProductOffersControllerEnd2EndTestUtils.*;

@ExtendWith(MockitoExtension.class)
class ProductOffersControllerEnd2EndTest {
    UUID sellerId;
    UUID productId;
    static DbConnection dbConnection;

    @BeforeAll
    static void startServer() throws IOException {
        Main.environment = Environment.TEST;
        Main.main(new String[0]);
        dbConnection = new DbConnection(Environment.TEST);
    }

    @BeforeEach
    void setup() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        sellerId = createSellerAndGetId(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE);
        productId = createProductForSellerAndGetId(sellerId, createProductRequest);
    }

    @AfterAll
    static void tearDownServer() {
        stop();
        Main.server.shutdown();
    }

    @AfterEach
    void dropDatabase() {
        dbConnection.dropTestDatabase();
    }

    @Test
    void givenValidProductIdAndOfferInfo_whenCreateOffer_thenResponseHasLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, not(emptyOrNullString()));
    }

    @Test
    void givenValidProductIdAndOfferInfo_whenCreateOffer_thenResponseHasCreatedStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(201);
    }

    @Test
    void givenNullName_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(null, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenNullName_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(null, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenNullName_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(null, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenNullEmail_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, null, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenNullEmail_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, null, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenNullEmail_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, null, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenNullPhone_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, null,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenNullPhone_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, null,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenNullPhone_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, null,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenNullAmount_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                null, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenNullAmount_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                null, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenNullAmount_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                null, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenNullMessage_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, null);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenNullMessage_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, null);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenNullMessage_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, null);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenNullProductId_whenCreateOffer_thenResponseHasNotFoundStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(null, createOfferRequest);

        createOfferResponse.then().statusCode(404);
    }

    @Test
    void givenNullProductId_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(null, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenValidNonExistentProductId_whenCreateOffer_thenResponseHasNotFoundStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(UUID.randomUUID(), createOfferRequest);

        createOfferResponse.then().statusCode(404);
    }

    @Test
    void givenValidNonExistentProductId_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(UUID.randomUUID(), createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenValidNonExistentProductId_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                OFFER_AMOUNT, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(UUID.randomUUID(), createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.ITEM_NOT_FOUND.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.ITEM_NOT_FOUND_EXCEPTION_DESC);
    }

    @Test
    void givenAmountLowerThanProductPrice_whenCreateOffer_thenResponseHasBadRequestStatus() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                100f, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().statusCode(400);
    }

    @Test
    void givenAmountLowerThanProductPrice_whenCreateOffer_thenResponseHasEmptyLocationHeader() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                100f, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        createOfferResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenAmountLowerThanProductPrice_whenCreateOffer_thenResponseHasErrorResponseAsBody() {
        var createOfferRequest = buildCreateOfferRequest(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE,
                100f, OFFER_MESSAGE);

        var createOfferResponse = createOfferForProduct(productId, createOfferRequest);

        var bodyJsonPath = createOfferResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }
}
