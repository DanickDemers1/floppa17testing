package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.BuyerItemsSellerCurrentDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;

public class BuyerItemsSellerCurrentAssembler {
    public static BuyerItemsSellerCurrentDto toDto(Buyer buyer) {
        if (buyer == null)
            throw new NullReferenceException();

        return new BuyerItemsSellerCurrentDto(buyer.getName(), buyer.getEmail(), buyer.getPhoneNumber());
    }
}
