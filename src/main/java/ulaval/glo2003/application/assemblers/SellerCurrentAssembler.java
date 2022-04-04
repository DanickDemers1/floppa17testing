package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.ProductSellerCurrentDto;
import ulaval.glo2003.application.dtos.SellerCurrentDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.buyer.Buyer;
import ulaval.glo2003.entities.offer.Offer;
import ulaval.glo2003.entities.product.Product;
import ulaval.glo2003.entities.seller.Seller;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class SellerCurrentAssembler {
    private final ProductSellerCurrentDto productSellerCurrentDto;

    public SellerCurrentAssembler(){
        productSellerCurrentDto = new ProductSellerCurrentDto();
    }

    public static SellerCurrentDto toDto(Seller seller, List<Product> sellerProducts, HashMap<UUID, List<Offer>> offers, HashMap<UUID, Buyer> buyers) {
        if (seller == null)
            throw new NullReferenceException();

        var sellerCurrentDto = new SellerCurrentDto();
        sellerCurrentDto.id = seller.getId().toString();
        sellerCurrentDto.name = seller.getName();
        sellerCurrentDto.bio = seller.getBio();
        sellerCurrentDto.createdAt = seller.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME);
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        sellerCurrentDto.birthDate = dateFormatter.format(seller.getBirthDate());
        sellerCurrentDto.products = sellerProducts.stream()
                .map(x -> ProductSellerCurrentAssembler.toDto(x, offers.containsKey(x.getId()) ? offers.get(x.getId()) : new ArrayList<>(), buyers))
                .collect(Collectors.toList());

        return sellerCurrentDto;
    }
}
