package ulaval.glo2003.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ulaval.glo2003.api.requests.CreateProductRequest;
import ulaval.glo2003.api.requests.CreateSellerRequest;
import ulaval.glo2003.entities.product.ProductCategory;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class End2EndTestUtils {
    public static final String ERROR_CODE = "code";
    public static final String ERROR_DESCRIPTION = "description";

    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_SELLER_ID = "X-Seller-Id";

    public static Response createSeller(String name, String bio, String birthDate) {
        return given()
                .contentType(ContentType.JSON)
                .body(new CreateSellerRequest(name, bio, birthDate))
                .when()
                .post(ControllerPaths.SELLERS + "/");
    }

    public static UUID createSellerAndGetId(String name, String bio, String birthDate) {
        var response = createSeller(name, bio, birthDate);
        return getIdFromResponseHeaderLocation(response, ControllerPaths.SELLERS + "/");
    }

    public static CreateProductRequest buildCreateProductRequest(String title, String description, float suggestedPrice,
                                                                 List<ProductCategory> categories) {
        return new CreateProductRequest(title, description, suggestedPrice,
                ProductCategory.fromProductCategoryListToStringList(categories));
    }

    public static Response createProductForSeller(UUID sellerId, CreateProductRequest createProductRequest) {
        return given()
                .header(HEADER_SELLER_ID, sellerId)
                .contentType(ContentType.JSON)
                .body(createProductRequest)
                .when()
                .post(ControllerPaths.PRODUCTS + "/");
    }

    public static UUID createProductForSellerAndGetId(UUID sellerId, CreateProductRequest createProductRequest) {
        var response = createProductForSeller(sellerId, createProductRequest);
        return getIdFromResponseHeaderLocation(response, ControllerPaths.PRODUCTS + "/");
    }

    public static UUID getIdFromResponseHeaderLocation(Response response, String path) {
        var locationHeader = response.getHeader(HEADER_LOCATION);
        var idBeginIndex = locationHeader.indexOf(path);
        var stringId = locationHeader.substring(idBeginIndex + path.length());
        return UUID.fromString(stringId);
    }
}
