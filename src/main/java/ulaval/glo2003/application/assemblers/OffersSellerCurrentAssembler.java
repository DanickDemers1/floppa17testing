package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.api.utils.DoubleUtils;
import ulaval.glo2003.application.dtos.OffersItemsSellerCurrentDto;
import ulaval.glo2003.application.dtos.OffersSellerCurrentDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalDouble;
import java.util.UUID;
import java.util.ArrayList;

public class OffersSellerCurrentAssembler {
    public static OffersSellerCurrentDto toDto(List<Offer> offers, HashMap<UUID, Buyer> buyers) {
        if (offers == null)
            throw new NullReferenceException();

        OptionalDouble mean = null;
        OptionalDouble min = null;
        OptionalDouble max = null;

        if (!offers.isEmpty()) {
           mean = offers.stream().mapToDouble(Offer::getAmount).average();
           min = offers.stream().mapToDouble(Offer::getAmount).min();
           max = offers.stream().mapToDouble(Offer::getAmount).max();
           mean = OptionalDouble.of(DoubleUtils.roundToTwoDecimals(mean.getAsDouble()));
           min = OptionalDouble.of(DoubleUtils.roundToTwoDecimals(min.getAsDouble()));
           max = OptionalDouble.of(DoubleUtils.roundToTwoDecimals(max.getAsDouble()));
        }

        List<OffersItemsSellerCurrentDto> items = new ArrayList<>();

        for (Offer offer : offers){
            Buyer buyer = buyers.get(offer.getId());
            items.add(OffersItemsSellerCurrentAssembler.toDto(offer,
                    BuyerItemsSellerCurrentAssembler.toDto(buyer)));
        }

        return new OffersSellerCurrentDto(offers.size(), mean, min, max, items);
    }
}
