package ulaval.glo2003.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SellerControllerEnd2EndTestUtils {
    private static final String HEADER_SELLER_ID = "X-Seller-Id";

    public static Response getSeller(String sellerId) {
        return when().get(ControllerPaths.SELLERS + "/" + sellerId);
    }

    public static Response getCurrentSeller(String sellerId){
        return given().header(HEADER_SELLER_ID,sellerId).urlEncodingEnabled(false)
                .when()
                .get(ControllerPaths.SELLERS + "/@me");
    }
}
