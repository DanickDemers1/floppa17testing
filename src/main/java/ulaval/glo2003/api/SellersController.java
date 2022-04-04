package ulaval.glo2003.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.assemblers.SellerResponseAssembler;
import ulaval.glo2003.api.requests.CreateSellerRequest;
import ulaval.glo2003.api.utils.StringUtils;
import ulaval.glo2003.application.assemblers.SellerAssembler;
import ulaval.glo2003.application.assemblers.SellerCurrentAssembler;
import ulaval.glo2003.application.exceptions.MissingParamException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.SellerFactory;
import ulaval.glo2003.infrastructure.persistence.repository.BuyerRepository;
import ulaval.glo2003.infrastructure.persistence.repository.OfferRepository;
import ulaval.glo2003.infrastructure.persistence.repository.ProductRepository;
import ulaval.glo2003.infrastructure.persistence.repository.SellerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path(ControllerPaths.SELLERS)
public class SellersController {
    private final String HEADER_SELLER_ID = "X-Seller-Id";

    private final SellerFactory sellerFactory;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;
    private final BuyerRepository buyerRepository;

    public SellersController(SellerFactory sellerFactory,
                             SellerRepository sellerRepository,
                             ProductRepository productRepository,
                             OfferRepository offerRepository,
                             BuyerRepository buyerRepository) {
        this.sellerFactory = sellerFactory;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.buyerRepository = buyerRepository;
    }

    @GET
    @Path("{sellerId}")
    public Response getSeller(@PathParam("sellerId") String sellerId) {
        var sellerUUID = StringUtils.parseStringToUUID(sellerId);

        var seller = sellerRepository.get(sellerUUID);
        var sellerProducts = productRepository.getAll().stream()
                .filter(product -> product.getSellerId().equals(sellerUUID))
                .collect(Collectors.toList());

        var productsOffers = new HashMap<UUID, List<Offer>>();
        for (Product product : sellerProducts)
            productsOffers.put(product.getId(), offerRepository.getOffersForProduct(product.getId()));

        var sellerDto = SellerAssembler.toDto(seller, sellerProducts, productsOffers);
        return SellerResponseAssembler.toGetSellerResponse(sellerDto);
    }

    @GET
    @Path("@me")
    public Response getCurrentSeller(@HeaderParam(HEADER_SELLER_ID) String sellerId) {

        if (sellerId == null){
            throw new MissingParamException();
        }

        var sellerUUID = StringUtils.parseStringToUUID(sellerId);

        var seller = sellerRepository.get(sellerUUID);
        var sellerProducts = productRepository.getAll().stream()
                .filter(product -> product.getSellerId().equals(sellerUUID))
                .collect(Collectors.toList());

        var buyers = new HashMap<UUID, Buyer>();

        var productsOffers = new HashMap<UUID, List<Offer>>();
        for (Product product : sellerProducts){
            productsOffers.put(product.getId(), offerRepository.getOffersForProduct(product.getId()));

            for (Offer offer : offerRepository.getOffersForProduct(product.getId()))
                buyers.put(offer.getId(), buyerRepository.get(offer.getBuyerId()));
        }

        var sellerCurrentDto = SellerCurrentAssembler.toDto(seller, sellerProducts, productsOffers,
                buyers);
        return SellerResponseAssembler.toGetSellerCurrentResponse(sellerCurrentDto);
    }

    @POST
    public Response createSeller(CreateSellerRequest request) {
        if (!request.allParametersAreDefined())
            throw new MissingParamException();

        var seller = sellerFactory.createSeller(request.name, request.bio, request.birthDate);
        sellerRepository.save(seller);

        return SellerResponseAssembler.toCreateSellerResponse(seller);
    }
}
