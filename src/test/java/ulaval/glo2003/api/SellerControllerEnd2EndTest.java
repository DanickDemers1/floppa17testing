package ulaval.glo2003.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.Main;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.io.IOException;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static spark.Spark.stop;
import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static ulaval.glo2003.api.End2EndTestUtils.*;
import static ulaval.glo2003.api.End2EndTestUtils.ERROR_CODE;
import static ulaval.glo2003.api.End2EndTestUtils.ERROR_DESCRIPTION;
import static ulaval.glo2003.api.End2EndTestUtils.HEADER_LOCATION;
import static ulaval.glo2003.api.SellerControllerEnd2EndTestUtils.*;

public class SellerControllerEnd2EndTest {
    static DbConnection dbConnection;

    @BeforeAll
    static void startServer() throws IOException {
        Main.environment = Environment.TEST;
        Main.main(new String[0]);
        dbConnection = new DbConnection(Environment.TEST);
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
    void givenValidSellerInfo_whenCreateSeller_thenResponseHasLocationHeader() {
        var createSellerResponse = createSeller(SELLER_NAME,SELLER_BIO,SELLER_BIRTHDATE);

        createSellerResponse.then().header(HEADER_LOCATION, not(emptyOrNullString()));
    }

    @Test
    void givenValidSellerInfo_whenCreateSeller_thenResponseHasCreatedStatus() {
        var createSellerResponse = createSeller(SELLER_NAME,SELLER_BIO,SELLER_BIRTHDATE);

        createSellerResponse.then().statusCode(201);
    }

    @Test
    void givenNullName_whenCreateSeller_thenResponseHasBadRequestStatus() {
        var createSellerResponse = createSeller(null, SELLER_BIO, SELLER_BIRTHDATE);

        createSellerResponse.then().statusCode(400);
    }

    @Test
    void givenNullBio_whenCreateSeller_thenResponseHasBadRequestStatus() {
        var createSellerResponse = createSeller(SELLER_NAME, null, SELLER_BIRTHDATE);

        createSellerResponse.then().statusCode(400);
    }

    @Test
    void givenRequestWithMissingParameter_whenCreateSeller_thenResponseHasEmptyLocationHeader() {
        var createSellerResponse = createSeller(null, null, SELLER_BIRTHDATE);

        createSellerResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenRequestWithMissingParameter_whenCreateSeller_thenResponseHasErrorResponseAsBody() {
        var createSellerResponse = createSeller(null, null, SELLER_BIRTHDATE);

        var bodyJsonPath = createSellerResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenBlankName_whenCreateSeller_thenResponseHasBadRequestStatus() {
        var createSellerResponse = createSeller(" ", SELLER_BIO, SELLER_BIRTHDATE);

        createSellerResponse.then().statusCode(400);
    }

    @Test
    void givenBlankBio_whenCreateSeller_thenResponseHasEmptyLocationHeader() {
        var createSellerResponse = createSeller(SELLER_NAME, " ", SELLER_BIRTHDATE);

        createSellerResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenBlankBirthDate_whenCreateSeller_thenResponseHasErrorResponseAsBody() {
        var createSellerResponse = createSeller(SELLER_NAME, SELLER_BIO, " ");

        var bodyJsonPath = createSellerResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenInvalidParameter_whenCreateSeller_thenReturnsBadRequestStatus() {
        var createSellerResponse = createSeller(SELLER_NAME, SELLER_BIO,"2018-02-28");

        createSellerResponse.then().statusCode(400);
    }

    @Test
    void givenInvalidParameter_whenCreateSeller_thenReturnsEmptyLocationHeader() {
        var createSellerResponse = createSeller(SELLER_NAME, SELLER_BIO,"1998-99-28");

        createSellerResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenInvalidParameter_whenCreateSeller_thenHasErrorResponseAsBody() {
        var createSellerResponse = createSeller(SELLER_NAME, SELLER_BIO,"1998-12-99");

        createSellerResponse.then().header(HEADER_LOCATION, emptyOrNullString());

        var bodyJsonPath = createSellerResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenValidSellerId_whenGetSeller_thenResponseHasOkResult() {
        var sellerId = createSellerAndGetId(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE).toString();

        Response response = getSeller(sellerId.substring(sellerId.lastIndexOf("/") + 1));

        response.then().statusCode(200);
    }

    @Test
    void givenValidSellerId_whenGetCurrentSeller_thenResponseHasOkResult() {
        var sellerId = createSellerAndGetId(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE).toString();

        Response response = getCurrentSeller(sellerId.substring(sellerId.lastIndexOf("/")+1));

        response.then().statusCode(200);
    }

    @Test
    void givenBlankSellerId_whenGetCurrentSeller_thenResponseHasItemNotFoundResult() {
        Response response = getCurrentSeller(" ");

        response.then().statusCode(404);
    }

    @Test
    void givenRequestWithMissingHeader_whenGetCurrentSeller_thenResponseHasMissingParamResult() {

        Response response = given().urlEncodingEnabled(false).when().get(ControllerPaths.SELLERS + "/@me");

        response.then().statusCode(400);
    }

    @Test
    void givenNotFoundSellerId_whenGetCurrentSeller_thenResponseHasNotFoundResult() {
        Response response = getCurrentSeller(UUID.randomUUID().toString());

        response.then().statusCode(404);
    }

    @Test
    void givenInvalidSellerId_whenGetSeller_thenResponseHasNotFoundResult() {
        Response response = getSeller(UUID.randomUUID().toString());

        response.then().statusCode(404);
    }
}
