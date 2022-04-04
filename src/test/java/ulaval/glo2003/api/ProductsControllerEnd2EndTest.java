package ulaval.glo2003.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ulaval.glo2003.Environment;
import ulaval.glo2003.Main;
import ulaval.glo2003.api.enums.ProductFilterParam;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.entities.product.ProductCategory;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static spark.Spark.stop;
import static ulaval.glo2003.EntityBuilderTestUtils.*;
import static ulaval.glo2003.api.End2EndTestUtils.*;
import static ulaval.glo2003.api.ProductsControllerEnd2EndTestUtils.*;
import static ulaval.glo2003.entities.product.ProductCategory.fromProductCategoryListToStringList;

@ExtendWith(MockitoExtension.class)
class ProductsControllerEnd2EndTest {
    UUID sellerId;
    static DbConnection dbConnection;

    @BeforeAll
    static void startServer() throws IOException {
        Main.environment = Environment.TEST;
        Main.main(new String[0]);
        dbConnection = new DbConnection(Environment.TEST);
    }

    @BeforeEach
    void setup() {
        sellerId = createSellerAndGetId(SELLER_NAME, SELLER_BIO, SELLER_BIRTHDATE);
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
    void givenValidProductId_whenGetProduct_thenResponseHasOkStatus() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        var createProductResponse = createProductForSeller(sellerId, createProductRequest);
        var productID = getIdFromResponseHeaderLocation(createProductResponse, "products/").toString();

        var getProductResponse = getProduct(productID);

        getProductResponse.then().statusCode(200);
    }

    @Test
    void givenInvalidProductId_whenGetProduct_thenResponseHasNotFoundStatus() {
        Response response = getProduct(INVALID_PRODUCT_ID);

        response.then().statusCode(404);
    }

    @Test
    void givenRandomProductId_whenGetProduct_thenResponseHasNotFoundStatus() {
        Response response = getProduct(RANDOM_PRODUCT_ID);

        response.then().statusCode(404);
    }

    @Test
    void givenValidSellerIdAndProductInfo_whenCreateProduct_thenResponseHasLocationHeader() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, not(emptyOrNullString()));
    }

    @Test
    void givenValidSellerIdAndProductInfo_whenCreateProduct_thenResponseHasCreatedStatus() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().statusCode(201);
    }

    @Test
    void givenValidSellerIdAndProductInfo_whenCreateProduct_thenCreatedProductIsAddedToSellerProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        createProductForSeller(sellerId, createProductRequest);

        var sellerProducts = getSellerProducts(sellerId);
        assertThat(sellerProducts.size()).isEqualTo(1);
        assertThat(sellerProducts.get(0).get("title")).isEqualTo(PRODUCT_TITLE);
        assertThat(sellerProducts.get(0).get("description")).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(String.valueOf(sellerProducts.get(0).get("suggestedPrice"))).isEqualTo(String.valueOf(PRODUCT_PRICE));
    }

    @Test
    void givenNoSellerId_whenCreateProduct_thenResponseHasBadRequestStatus() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProduct(createProductRequest);

        createProductResponse.then().statusCode(400);
    }

    @Test
    void givenRequestWithMissingParameter_whenCreateProduct_thenResponseHasBadRequestStatus() {
        var createProductRequest = buildCreateProductRequest(null, null,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().statusCode(400);
    }

    @Test
    void givenInvalidSellerId_whenCreateProduct_thenResponseHasEmptyLocationHeader() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(UUID.randomUUID(), createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenInvalidSellerId_whenCreateProduct_thenResponseHasErrorResponseAsBody() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(UUID.randomUUID(), createProductRequest);

        var bodyJsonPath = createProductResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.ITEM_NOT_FOUND.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.ITEM_NOT_FOUND_EXCEPTION_DESC);
    }

    @Test
    void givenRequestWithMissingParameter_whenCreateProduct_thenResponseHasEmptyLocationHeader() {
        var createProductRequest = buildCreateProductRequest(null, null,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenRequestWithMissingParameter_whenCreateProduct_thenResponseHasErrorResponseAsBody() {
        var createProductRequest = buildCreateProductRequest(null, null,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        var bodyJsonPath = createProductResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.MISSING_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.MISSING_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenRequestWithBlankParameter_whenCreateProduct_thenResponseHasBadRequestStatus() {
        var createProductRequest = buildCreateProductRequest("", "",
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().statusCode(400);
    }

    @Test
    void givenRequestWithBlankParameter_whenCreateProduct_thenResponseHasEmptyLocationHeader() {
        var createProductRequest = buildCreateProductRequest("", "",
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenRequestWithBlankParameter_whenCreateProduct_thenResponseHasErrorResponseAsBody() {
        var createProductRequest = buildCreateProductRequest("", "",
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        var bodyJsonPath = createProductResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenRequestWithInvalidParameter_whenCreateProduct_thenResponseHasBadRequestStatus() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().statusCode(400);
    }

    @Test
    void givenRequestWithInvalidParameter_whenCreateProduct_thenResponseHasEmptyLocationHeader() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenRequestWithInvalidParameter_whenCreateProduct_thenResponseHasErrorResponseHasBody() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                0.99f, PRODUCT_CATEGORIES);

        var createProductResponse = createProductForSeller(sellerId, createProductRequest);

        createProductResponse.then().header(HEADER_LOCATION, emptyOrNullString());

        var bodyJsonPath = createProductResponse.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenAllExistentFiterParameters_whenGetProductListFiltered_thenResponseHasOkStatus() {
        var productListFilteredResponse = getProductListFilteredResponse();

        productListFilteredResponse.then().statusCode(200);
    }

    @Test
    void givenTitleAsFilterParam_whenGetProductListFiltered_thenResponseContainsProductsWithMatchingTitle() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.TITLE, TITLE_FILTER_IN)
                .when()
                .get(ControllerPaths.PRODUCTS);

        var bodyJsonPath = getProductListFilteredResponse.then().extract().body().jsonPath();

        assertThat(bodyJsonPath.get("products").toString()).contains(PRODUCT_TITLE);
    }

    @Test
    void givenTitleAsFilterParam_whenGetProductListFiltered_thenResponseDoesNotContainsProductsThatDoNotMatchTitle() {
        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.TITLE, TITLE_FILTER_OUT)
                .when()
                .get(ControllerPaths.PRODUCTS);

        var bodyJsonPath = getProductListFilteredResponse.then().extract().body().jsonPath();

        assertThat(bodyJsonPath.get("products").toString()).doesNotContain(PRODUCT_TITLE);
    }

    @Test
    void givenMaxPriceAsFilterParam_whenGetProductListFiltered_thenResponseContainsProductsWithPriceUnderGivenMaxPrice() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.MAX_PRICE, HIGHER_PRICE)
                .when()
                .get(ControllerPaths.PRODUCTS);

        var bodyJsonPath = getProductListFilteredResponse.then().extract().body().jsonPath();

        assertThat(bodyJsonPath.get("products").toString()).contains(PRODUCT_TITLE);
    }

    @Test
    void givenSellerIdAsFilterParam_whenGetProductListFiltered_thenResponseContainsProductsWithMatchingSellerId() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given()
                .queryParam(ProductFilterParam.SELLER_ID, sellerId.toString())
                .when()
                .get(ControllerPaths.PRODUCTS);

        ArrayList<LinkedHashMap<String, String>> responseProducts = getProductListFilteredResponse
                .then().extract().body().jsonPath().get("products");

        assertThat(responseProducts.size()).isEqualTo(1);
        assertThat(responseProducts.toString()).contains(sellerId.toString());
    }

    @Test
    void givenSellerIdAsFilterParamAndEmptySellerId_whenGetProductListFiltered_thenResponseContainsNoProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.SELLER_ID, "")
                .when()
                .get(ControllerPaths.PRODUCTS);

        ArrayList<LinkedHashMap<String, String>> responseProducts = getProductListFilteredResponse
                .then().extract().body().jsonPath().get("products");
        assertThat(responseProducts.size()).isEqualTo(0);
    }

    @Test
    void givenMinPriceAsFilterParamWithZeroAsMinPrice_whenGetProductListFiltered_thenResponseContainsAllProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.MIN_PRICE, 0)
                .when()
                .get(ControllerPaths.PRODUCTS);

        var bodyJsonPath = getProductListFilteredResponse.then().extract().body().jsonPath();

        assertThat(bodyJsonPath.get("products").toString()).contains(PRODUCT_TITLE);
    }

    @Test
    void givenMaxPriceAsFilterParam_whenGetProductListFiltered_thenResponseContainsProductWithPricesUnderGivenMaxPrice() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnHigherPrice = getProductFilteredBasedOnMaxPrice(HIGHER_PRICE);

        var bodyJsonPath = productFilteredBasedOnHigherPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).contains("suggestedPrice");
    }

    @Test
    void givenMaxPriceAsFilterParamWithLowestPriceAsMaxPrice_whenGetProductListFiltered_thenResponseContainsNoProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnLowerPrice = getProductFilteredBasedOnMaxPrice(LOWER_PRICE);

        var bodyJsonPath = productFilteredBasedOnLowerPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).doesNotContain(Float.toString(PRODUCT_PRICE));
    }

    @Test
    void givenMaxPriceAsFilterParamWithNegativePriceAsMaxPrice_whenGetProductListFiltered_thenResponseContainsAllProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnLowerPrice = getProductFilteredBasedOnMaxPrice(NEGATIVE_PRICE);

        var bodyJsonPath = productFilteredBasedOnLowerPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).contains(Float.toString(PRODUCT_PRICE));
    }

    @Test
    void givenInvalidMaxPriceParameter_whenGetProductListFiltered_thenResponseHasEmptyLocationHeader() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnLowerPrice = getProductFilteredBasedOnInvalidMaxPrice();

        productFilteredBasedOnLowerPrice.then().header(HEADER_LOCATION, emptyOrNullString());
    }

    @Test
    void givenInvalidMaxPriceParameter_whenGetProductListFiltered_thenResponseHasErrorResponseBody() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnLowerPrice = getProductFilteredBasedOnInvalidMaxPrice();

        var bodyJsonPath = productFilteredBasedOnLowerPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get(ERROR_CODE).toString()).isEqualTo(ExceptionType.INVALID_PARAMETER.enumToString());
        assertThat(bodyJsonPath.get(ERROR_DESCRIPTION).toString()).isEqualTo(Dictionary.INVALID_PARAMETER_EXCEPTION_DESC);
    }

    @Test
    void givenMinPriceAsFilterParamWithHighestPriceAsMinPrice_whenGetProductListFiltered_thenResponseContainsNoProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnHigherPrice = getProductFilteredBasedOnMinPrice(HIGHER_PRICE);

        var bodyJsonPath = productFilteredBasedOnHigherPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).doesNotContain(Float.toString(PRODUCT_PRICE));
    }

    @Test
    void givenMinPriceAsFilterParamWithLowestPriceAsMinPrice_whenGetProductListFiltered_thenResponseContainsAllProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnLowerPrice = getProductFilteredBasedOnMinPrice(LOWER_PRICE);

        var bodyJsonPath = productFilteredBasedOnLowerPrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).contains("suggestedPrice");
    }

    @Test
    void givenMinPriceAsFilterParameterWithNegativePriceAsMinPrice_whenGetProductListFiltered_thenResponseContainsAllProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        var productFilteredBasedOnNegativePrice = getProductFilteredBasedOnMinPrice(NEGATIVE_PRICE);

        var bodyJsonPath = productFilteredBasedOnNegativePrice.then().extract().body().jsonPath();
        assertThat(bodyJsonPath.get("products").toString()).contains(Float.toString(PRODUCT_PRICE));
    }

    @Test
    void givenCategoriesAsFilterParam_whenGetProductListFiltered_thenResponseContainsProductsThatMatchGivenCategory() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.CATEGORIES, CATEGORIES_FILTER_EXISTENT)
                .when()
                .get(ControllerPaths.PRODUCTS);

        ArrayList<LinkedHashMap<String, List<String>>> responseProducts = getProductListFilteredResponse
                .then().extract().body().jsonPath().get("products");


        List<String> productCategories = responseProducts.get(responseProducts.size()-1).get("categories");

        assertThat(fromProductCategoryListToStringList(CATEGORIES_FILTER_EXISTENT)).containsAnyIn(productCategories);
    }

    @Test
    void givenCategoriesAsFilterParamAndNonExistentCategory_whenGetProductListFiltered_thenResponseContainsNoProducts() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given().queryParam(ProductFilterParam.CATEGORIES, CATEGORIES_FILTER_NON_EXISTENT)
                .when()
                .get(ControllerPaths.PRODUCTS);

        ArrayList<LinkedHashMap<String, ProductCategory>> responseProducts = getProductListFilteredResponse
                .then().extract().body().jsonPath().get("products");
        assertThat(responseProducts.size()).isEqualTo(0);
    }

    @Test
    void givenMultipleFilterParams_whenGetProductListFiltered_thenResponseContainsProductsThatMatchAtLeastOneFilter() {
        var createProductRequest = buildCreateProductRequest(PRODUCT_TITLE, PRODUCT_DESCRIPTION,
                PRODUCT_PRICE, PRODUCT_CATEGORIES);
        createProductForSeller(sellerId, createProductRequest);

        Response getProductListFilteredResponse = given()
                .queryParam(ProductFilterParam.TITLE, TITLE_FILTER_IN)
                .queryParam(ProductFilterParam.SELLER_ID, sellerId.toString())
                .queryParam(ProductFilterParam.MIN_PRICE, 0)
                .queryParam(ProductFilterParam.MAX_PRICE, HIGHER_PRICE)
                .when()
                .get(ControllerPaths.PRODUCTS);

        var bodyJsonPath = getProductListFilteredResponse.then().extract().body().jsonPath();

        assertThat(bodyJsonPath.get("products").toString()).contains(PRODUCT_TITLE);
    }
}
