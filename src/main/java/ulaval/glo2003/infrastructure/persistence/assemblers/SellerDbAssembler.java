package ulaval.glo2003.infrastructure.persistence.assemblers;

import ulaval.glo2003.entities.seller.Seller;
import ulaval.glo2003.infrastructure.persistence.models.SellerModel;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static ulaval.glo2003.api.utils.DateUtils.parseDateToString;
import static ulaval.glo2003.api.utils.DateUtils.parseStringToDate;

public class SellerDbAssembler {
    public static SellerModel toModel(Seller seller) {

        var sellerModel = new SellerModel();
        sellerModel.id = seller.getId();
        sellerModel.name = seller.getName();
        sellerModel.bio = seller.getBio();
        sellerModel.birthDate =  parseDateToString(seller.getBirthDate());
        sellerModel.createdAt = seller.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);

        return sellerModel;
    }

    public static Seller toEntity(SellerModel sellerModel) {
        var seller = new Seller(
                sellerModel.id,
                OffsetDateTime.parse(sellerModel.createdAt),
                sellerModel.name,
                sellerModel.bio,
                parseStringToDate(sellerModel.birthDate));

        return  seller;
    }
}
