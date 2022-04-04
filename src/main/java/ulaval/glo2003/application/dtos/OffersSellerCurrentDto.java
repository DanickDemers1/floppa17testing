package ulaval.glo2003.application.dtos;

import java.util.List;
import java.util.OptionalDouble;

public class OffersSellerCurrentDto {
    public int count;
    public OptionalDouble mean;
    public OptionalDouble min;
    public OptionalDouble max;
    public List<OffersItemsSellerCurrentDto> items;

    public OffersSellerCurrentDto(int count, OptionalDouble mean, OptionalDouble min, OptionalDouble max, List<OffersItemsSellerCurrentDto> items) {
        this.count = count;
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.items = items;
    }
}
