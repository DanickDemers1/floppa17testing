package ulaval.glo2003.infrastructure.persistence.assemblers;

import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.infrastructure.persistence.models.BuyerModel;
import ulaval.glo2003.infrastructure.persistence.models.OfferModel;
import ulaval.glo2003.infrastructure.persistence.models.ProductModel;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OfferDbAssembler {
    public static OfferModel toModel(Offer offer, ProductModel productModel,
                                     BuyerModel buyerModel) {
        var offerModel = new OfferModel();

        offerModel.id = offer.getId();
        offerModel.amount = offer.getAmount();
        offerModel.message = offer.getMessage();
        offerModel.createdAt = offer.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        offerModel.productModel = productModel;
        offerModel.buyerModel = buyerModel;

        return offerModel;
    }

    public static Offer toEntity(OfferModel offerModel) {
        return new Offer(offerModel.id,
                OffsetDateTime.parse(offerModel.createdAt),
                offerModel.productModel.id,
                offerModel.buyerModel.id,
                offerModel.amount,
                offerModel.message);
    }
}
