package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.dtos.SellerCurrentDto;
import ulaval.glo2003.application.dtos.SellerDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.seller.Seller;

import java.net.URI;

public class SellerResponseAssembler {
    public static Response toCreateSellerResponse(Seller seller) {
        if (seller == null)
            throw new NullReferenceException();

        return Response.created(URI.create(ControllerPaths.SELLERS + "/" + seller.getId())).build();
    }

    public static Response toGetSellerResponse(SellerDto seller) {
        if (seller == null)
            throw new NullReferenceException();

        return Response.status(200).entity(seller).build();
    }

    public static Response toGetSellerCurrentResponse(SellerCurrentDto seller){
        if (seller == null)
            throw new NullReferenceException();

        return Response.status(200).entity(seller).build();
    }

}
