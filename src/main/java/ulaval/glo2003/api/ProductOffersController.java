package ulaval.glo2003.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.assemblers.OfferResponseAssembler;
import ulaval.glo2003.api.requests.CreateOfferRequest;
import ulaval.glo2003.api.utils.StringUtils;
import ulaval.glo2003.application.exceptions.InvalidParamException;
import ulaval.glo2003.application.exceptions.MissingParamException;
import ulaval.glo2003.entities.buyer.BuyerFactory;
import ulaval.glo2003.entities.offer.OfferFactory;
import ulaval.glo2003.infrastructure.persistence.repository.BuyerRepository;
import ulaval.glo2003.infrastructure.persistence.repository.OfferRepository;
import ulaval.glo2003.infrastructure.persistence.repository.ProductRepository;

@Path(ControllerPaths.PRODUCT_OFFERS)
public class ProductOffersController {
    private final OfferFactory offerFactory;
    private final BuyerFactory buyerFactory;
    private final OfferRepository offerRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;

    public ProductOffersController(OfferFactory offerFactory,
                                   BuyerFactory buyerFactory,
                                   OfferRepository offerRepository,
                                   BuyerRepository buyerRepository,
                                   ProductRepository productRepository) {
        this.offerFactory = offerFactory;
        this.buyerFactory = buyerFactory;
        this.offerRepository = offerRepository;
        this.buyerRepository = buyerRepository;
        this.productRepository = productRepository;
    }

    @POST
    public Response createOffer(@PathParam("productId") String productId, CreateOfferRequest request) {
        if (!request.allParametersAreDefined() || productId == null)
            throw new MissingParamException();

        var productUUID = StringUtils.parseStringToUUID(productId);
        var product = productRepository.get(productUUID);

        if (request.amount < product.getSuggestedPrice())
            throw new InvalidParamException();

        var buyer = buyerFactory.createBuyer(request.name, request.email, request.phoneNumber);
        buyerRepository.save(buyer);

        var offer = offerFactory.createOffer(productUUID, buyer.getId(), request.amount, request.message);
        offerRepository.save(offer);

        return OfferResponseAssembler.toCreateOfferResponse(offer);
    }
}
