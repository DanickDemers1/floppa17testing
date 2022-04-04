package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.dtos.ProductsFilteredDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;

import java.util.List;

public class ProductsFilteredAssembler {
    public static ProductsFilteredDto toDto(List<ProductDto> productsFiltered) {
        if (productsFiltered == null)
            throw new NullReferenceException();

        var productsFilteredDto = new ProductsFilteredDto();
        productsFilteredDto.products = productsFiltered;

        return productsFilteredDto;
    }
}
