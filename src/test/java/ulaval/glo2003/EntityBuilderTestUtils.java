package ulaval.glo2003;

import ulaval.glo2003.api.utils.DateUtils;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.product.ProductCategory;
import ulaval.glo2003.entities.seller.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityBuilderTestUtils {
    public static final String SELLER_NAME = "Isell Alot";
    public static final String SELLER_BIO = "A great product seller";
    public static final String SELLER_BIRTHDATE = "1998-12-10";

    public static final String PRODUCT_TITLE = "Hockey stick";
    public static final String PRODUCT_DESCRIPTION = "A great hockey stick";
    public static final float PRODUCT_PRICE = 119.99f;
    public static final List<ProductCategory> PRODUCT_CATEGORIES =
            new ArrayList<>(List.of(ProductCategory.OTHER,ProductCategory.SPORTS));

    public static final String TITLE_FILTER_IN = "st";
    public static final String TITLE_FILTER_OUT = "this is not in the title";

    public static final float LOWER_PRICE = 49.99f;
    public static final float HIGHER_PRICE = 149.99f;
    public static final float NEGATIVE_PRICE = -100.00f;

    public static final String BUYER_NAME = "John Doe";
    public static final String BUYER_EMAIL = "john.doe@email.com";
    public static final String BUYER_PHONE = "18191234567";

    public static final float OFFER_AMOUNT = 199.99f;
    public static final String OFFER_MESSAGE = "Donec porttitor interdum lacus sed finibus. " +
            "Nam pulvinar facilisis posuere. Maecenas vel lorem amet.";

    public static final List<ProductCategory> CATEGORIES_FILTER_EXISTENT =
            new ArrayList<>(List.of(ProductCategory.OTHER,ProductCategory.BEAUTY));
    public static final List<ProductCategory> CATEGORIES_FILTER_NON_EXISTENT =
            new ArrayList<>(List.of(ProductCategory.APPAREL));

    public static Seller getDefaultSeller() {
        return new Seller(SELLER_NAME, SELLER_BIO, DateUtils.parseStringToDate(SELLER_BIRTHDATE));
    }

    public static Product getDefaultProduct(UUID sellerId) {
        return new Product(PRODUCT_TITLE, PRODUCT_DESCRIPTION, PRODUCT_PRICE, PRODUCT_CATEGORIES, sellerId);
    }

    public static Buyer getDefaultBuyer() {
        return new Buyer(BUYER_NAME, BUYER_EMAIL, BUYER_PHONE);
    }

    public static Offer getDefaultOffer(UUID productId, UUID buyerId) {
        return new Offer(productId, buyerId, OFFER_AMOUNT, OFFER_MESSAGE);
    }
}
