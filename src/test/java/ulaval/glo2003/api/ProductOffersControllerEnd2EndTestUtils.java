package ulaval.glo2003.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ulaval.glo2003.api.requests.CreateOfferRequest;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class ProductOffersControllerEnd2EndTestUtils {
    public static CreateOfferRequest buildCreateOfferRequest(String name, String email, String phoneNumber, Float amount, String message) {
        return new CreateOfferRequest(name, email, phoneNumber, amount, message);
    }

    public static Response createOfferForProduct(UUID productId, CreateOfferRequest createOfferRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(createOfferRequest)
                .when()
                .post(ControllerPaths.PRODUCTS + "/" + productId + "/offers");
    }
}
