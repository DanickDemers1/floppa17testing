package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.ProductSellerCurrentDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.product.ProductCategory;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductSellerCurrentAssembler {
    public static ProductSellerCurrentDto toDto(Product product, List<Offer> offers, HashMap<UUID, Buyer> buyers) {
        if (product == null)
            throw new NullReferenceException();

        var productDto = new ProductSellerCurrentDto();
        productDto.id = product.getId().toString();
        productDto.createdAt = product.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        productDto.title = product.getTitle();
        productDto.description = product.getDescription();
        productDto.suggestedPrice = product.getSuggestedPrice();
        productDto.categories = product.getCategories().stream()
                .map(ProductCategory::enumToString)
                .collect(Collectors.toList());
        productDto.offers = OffersSellerCurrentAssembler.toDto(offers, buyers);

        return productDto;
    }
}
