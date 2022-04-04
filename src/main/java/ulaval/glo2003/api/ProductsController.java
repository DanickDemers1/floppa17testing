package ulaval.glo2003.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.assemblers.ProductResponseAssembler;
import ulaval.glo2003.api.enums.ProductFilterParam;
import ulaval.glo2003.api.requests.CreateProductRequest;
import ulaval.glo2003.api.utils.StringUtils;
import ulaval.glo2003.application.assemblers.ProductAssembler;
import ulaval.glo2003.application.assemblers.ProductsFilteredAssembler;
import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;
import ulaval.glo2003.application.exceptions.MissingParamException;
import ulaval.glo2003.entities.product.ProductFactory;
import ulaval.glo2003.entities.product.ProductFilter;
import ulaval.glo2003.infrastructure.persistence.repository.OfferRepository;
import ulaval.glo2003.infrastructure.persistence.repository.ProductRepository;
import ulaval.glo2003.infrastructure.persistence.repository.SellerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ulaval.glo2003.entities.utils.StringUtils.parseStringToFloat;
import static ulaval.glo2003.entities.product.ProductCategory.fromStringListToProductCategoryList;

@Path(ControllerPaths.PRODUCTS)
public class ProductsController {
    private final String HEADER_SELLER_ID = "X-Seller-Id";

    private final ProductFactory productFactory;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final OfferRepository offerRepository;

    public ProductsController(ProductFactory productFactory,
                              ProductRepository productRepository,
                              SellerRepository sellerRepository,
                              OfferRepository offerRepository) {
        this.productFactory = productFactory;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.offerRepository = offerRepository;
    }

    @GET
    @Path("{productId}")
    public Response getProduct(@PathParam("productId") String productId) {
        var productUUID = StringUtils.parseStringToUUID(productId);
        var foundProduct = productRepository.get(productUUID);

        var seller = sellerRepository.get(foundProduct.getSellerId());
        var productOffers = offerRepository.getOffersForProduct(productUUID);
        var foundProductDto = ProductAssembler.toDto(foundProduct, seller, productOffers);

        return ProductResponseAssembler.toGetProductResponse(foundProductDto);
    }

    @GET
    public Response getProductListFiltered(@QueryParam(ProductFilterParam.SELLER_ID) String sellerId,
                                           @QueryParam(ProductFilterParam.TITLE) String title,
                                           @DefaultValue("-1") @QueryParam(ProductFilterParam.MIN_PRICE) String minPrice,
                                           @DefaultValue("-1") @QueryParam(ProductFilterParam.MAX_PRICE) String maxPrice,
                                           @QueryParam(ProductFilterParam.CATEGORIES) List<String> categories) {

        var floatMinPrice = parseStringToFloat(minPrice);
        var floatMaxPrice = parseStringToFloat(maxPrice);

        var productFilter = new ProductFilter(sellerId, title, floatMinPrice, floatMaxPrice,
                fromStringListToProductCategoryList(categories));

        var productsDto = new ArrayList<ProductDto>();
        for (var product : productFilter.filterProductsList(productRepository.getAll())) {
            var seller = sellerRepository.get(product.getSellerId());
            var productOffers = offerRepository.getOffersForProduct(product.getId());
            productsDto.add(ProductAssembler.toDto(product, seller, productOffers));
        }

        return ProductResponseAssembler.toGetFilteredProductsResponse(ProductsFilteredAssembler.toDto(productsDto));
    }

    @POST
    public Response createProduct(@HeaderParam(HEADER_SELLER_ID) UUID sellerId, CreateProductRequest request) {
        if (!request.allParametersAreDefined() || sellerId == null)
            throw new MissingParamException();

        if (!sellerRepository.exists(sellerId))
            throw new ItemNotFoundException();

        var product = productFactory.createProduct(request.title, request.description, request.suggestedPrice,
                request.getProductCategories(), sellerId);


        productRepository.save(product);
        return ProductResponseAssembler.toCreateProductResponse(product);
    }
}
