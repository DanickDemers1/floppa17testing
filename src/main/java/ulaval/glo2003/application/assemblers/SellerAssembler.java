package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.SellerDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SellerAssembler {
    public static SellerDto toDto(Seller seller, List<Product> sellerProducts, HashMap<UUID, List<Offer>> offers) {
        if (seller == null)
            throw new NullReferenceException();
        if (sellerProducts == null)
            throw new NullReferenceException();
        if (offers == null)
            throw new NullReferenceException();

        var sellerDto = new SellerDto();
        sellerDto.id = seller.getId().toString();
        sellerDto.name = seller.getName();
        sellerDto.bio = seller.getBio();
        sellerDto.createdAt = seller.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        sellerDto.products = sellerProducts.stream()
                .map(x -> ProductShortAssembler.toDto(x, offers.containsKey(x.getId()) ? offers.get(x.getId()) : new ArrayList<>()))
                .collect(Collectors.toList());

        return sellerDto;
    }
}
