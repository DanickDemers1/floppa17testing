package ulaval.glo2003.infrastructure.persistence.assemblers;

import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.infrastructure.persistence.models.BuyerModel;

public class BuyerDbAssembler {
    public static BuyerModel toModel(Buyer buyer) {
        var buyerModel = new BuyerModel();

        buyerModel.id = buyer.getId();
        buyerModel.name = buyer.getName();
        buyerModel.email = buyer.getEmail();
        buyerModel.phoneNumber = buyer.getPhoneNumber();

        return buyerModel;
    }

    public static Buyer toEntity(BuyerModel buyerModel) {
        return new Buyer(buyerModel.id, buyerModel.name,
                buyerModel.email, buyerModel.phoneNumber);
    }
}
