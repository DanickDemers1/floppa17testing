package ulaval.glo2003.application.dtos;

import java.util.ArrayList;
import java.util.List;

public class SellerDto {
    public String id;
    public String name;
    public String createdAt;
    public String bio;
    public List<ProductShortDto> products = new ArrayList<>();
}
