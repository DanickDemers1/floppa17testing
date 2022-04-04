package ulaval.glo2003.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ulaval.glo2003.api.enums.ProductFilterParam;
import ulaval.glo2003.api.requests.CreateProductRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ProductsControllerEnd2EndTestUtils {
    public static final String INVALID_PRODUCT_ID = "123-test-invalid";
    public static final String RANDOM_PRODUCT_ID = UUID.randomUUID().toString();

    public static Response getProduct(String productId) {
        return when().get(ControllerPaths.PRODUCTS + "/" + productId);
    }

    public static Response createProduct(CreateProductRequest createProductRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(createProductRequest)
                .when()
                .post(ControllerPaths.PRODUCTS + "/");
    }

    public static Response getSeller(UUID sellerId) {
        return given()
                .when()
                .get(ControllerPaths.SELLERS + "/" + sellerId);
    }

    public static ArrayList<LinkedHashMap<String, String>> getSellerProducts(UUID sellerId) {
        var getSellerResponse = getSeller(sellerId);
        var bodyJsonPath = getSellerResponse.then().extract().body().jsonPath();
        return bodyJsonPath.get("products");
    }

    public static Response getProductListFilteredResponse(){
        return given()
                .queryParam(ProductFilterParam.SELLER_ID, "")
                .queryParam(ProductFilterParam.TITLE, "is")
                .queryParam(ProductFilterParam.MIN_PRICE, "0")
                .queryParam(ProductFilterParam.MAX_PRICE, "9999999")
                .queryParam(ProductFilterParam.CATEGORIES, "sports")
                .when()
                .get(ControllerPaths.PRODUCTS + "");
    }

    public static Response getProductFilteredBasedOnMaxPrice(float price){
        return given().queryParam(ProductFilterParam.MAX_PRICE, price)
                .when()
                .get(ControllerPaths.PRODUCTS);
    }

    public static Response getProductFilteredBasedOnInvalidMaxPrice(){
        return given().queryParam(ProductFilterParam.MAX_PRICE, "some invalid string")
                .when()
                .get(ControllerPaths.PRODUCTS);
    }

    public static Response getProductFilteredBasedOnMinPrice(float price){
        return given().queryParam(ProductFilterParam.MIN_PRICE, price)
                .when()
                .get(ControllerPaths.PRODUCTS);
    }
}
