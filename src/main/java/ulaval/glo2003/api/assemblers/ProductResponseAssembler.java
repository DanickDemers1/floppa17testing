package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.ControllerPaths;
import ulaval.glo2003.application.dtos.ProductDto;
import ulaval.glo2003.application.dtos.ProductsFilteredDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;
import ulaval.glo2003.entities.product.Product;

import java.net.URI;

public class ProductResponseAssembler {
    public static Response toCreateProductResponse(Product product) {
        if (product == null)
            throw new NullReferenceException();
        return Response.created(URI.create(ControllerPaths.PRODUCTS + "/" + product.getId())).build();
    }

    public static Response toGetProductResponse(ProductDto productDto) {
        if (productDto == null)
            throw new NullReferenceException();
        return Response.status(200).entity(productDto).build();
    }

    public static Response toGetFilteredProductsResponse(ProductsFilteredDto productsFilteredDto) {
        if (productsFilteredDto == null)
            throw new NullReferenceException();
        return Response.status(200).entity(productsFilteredDto).build();
    }
}
