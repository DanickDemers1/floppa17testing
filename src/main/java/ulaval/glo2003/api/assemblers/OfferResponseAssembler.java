package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;

import java.net.URI;

public class OfferResponseAssembler {
    public static Response toCreateOfferResponse(Offer offer) {
        if (offer == null)
            throw new NullReferenceException();

        var path = URI.create(ControllerPaths.PRODUCTS + "/" + offer.getProductId() + "/offers/" + offer.getId());
        return Response.created(path).build();
    }
}
