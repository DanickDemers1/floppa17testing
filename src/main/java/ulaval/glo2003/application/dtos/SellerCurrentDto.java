package ulaval.glo2003.application.dtos;

import java.util.ArrayList;
import java.util.List;

public class SellerCurrentDto {
    public String id;
    public String name;
    public String createdAt;
    public String bio;
    public String birthDate;
    public List<ProductSellerCurrentDto> products = new ArrayList<>();
}
