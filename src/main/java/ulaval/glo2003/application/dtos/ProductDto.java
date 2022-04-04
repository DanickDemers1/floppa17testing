package ulaval.glo2003.application.dtos;

import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    public String id;
    public String createdAt;
    public String title;
    public String description;
    public float suggestedPrice;
    public List<String> categories = new ArrayList<>();
    public SellerShortDto seller;
    public OffersDto offers;
}
