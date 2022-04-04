package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.product.ProductCategory;
import ulaval.glo2003.entities.seller.Seller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAssembler {
    public static ProductDto toDto(Product product, Seller seller, List<Offer> offers) {
        if (product == null)
            throw new NullReferenceException();
        if (seller == null)
            throw new NullReferenceException();

        var productDto = new ProductDto();
        productDto.id = product.getId().toString();
        productDto.createdAt = product.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        productDto.title = product.getTitle();
        productDto.description = product.getDescription();
        productDto.suggestedPrice = product.getSuggestedPrice();

        productDto.categories = product.getCategories().stream()
                .map(ProductCategory::enumToString)
                .collect(Collectors.toList());
        productDto.seller = SellerShortAssembler.toDto(seller);
        productDto.offers = OffersAssembler.toDto(offers);

        return productDto;
    }
}
