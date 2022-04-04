package ulaval.glo2003.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.api.exceptions.ErrorResponse;
import ulaval.glo2003.application.exceptions.ItemNotFoundException;

public class ItemNotFoundExceptionMapper implements ExceptionMapper<ItemNotFoundException> {
    @Override
    public Response toResponse(ItemNotFoundException e) {
        return Response.status(404)
                .entity(new ErrorResponse(ExceptionType.ITEM_NOT_FOUND, Dictionary.ITEM_NOT_FOUND_EXCEPTION_DESC))
                .build();
    }
}
