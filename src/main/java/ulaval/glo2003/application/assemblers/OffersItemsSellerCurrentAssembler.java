package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.BuyerItemsSellerCurrentDto;
import ulaval.glo2003.application.dtos.OffersItemsSellerCurrentDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;
import java.time.format.DateTimeFormatter;

public class OffersItemsSellerCurrentAssembler {
    public static OffersItemsSellerCurrentDto toDto(Offer offer, BuyerItemsSellerCurrentDto buyer) {
        if (offer == null)
            throw new NullReferenceException();

        return new OffersItemsSellerCurrentDto(offer.getId().toString(),
                offer.getAmount(), offer.getMessage(),
                offer.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME), buyer);
    }
}
