package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.api.utils.DoubleUtils;
import ulaval.glo2003.application.dtos.OffersDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;

import java.util.List;

public class OffersAssembler {
    public static OffersDto toDto(List<Offer> offers) {
        if (offers == null)
            throw new NullReferenceException();

        var mean = offers.stream().mapToDouble(Offer::getAmount).average();
        return new OffersDto(offers.size(), DoubleUtils.roundToTwoDecimals(mean.isPresent() ? mean.getAsDouble() : 0));
    }
}
