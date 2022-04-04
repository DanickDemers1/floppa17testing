package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.SellerShortDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.seller.Seller;

public class SellerShortAssembler {
    public static SellerShortDto toDto(Seller seller) {
        if (seller == null)
            throw new NullReferenceException();

        var sellerShortDto = new SellerShortDto();
        sellerShortDto.id = seller.getId().toString();
        sellerShortDto.name = seller.getName();

        return sellerShortDto;
    }
}
