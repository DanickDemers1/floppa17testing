package ulaval.glo2003.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.api.exceptions.ErrorResponse;
import ulaval.glo2003.application.exceptions.InvalidParamException;

public class InvalidParamExceptionMapper implements ExceptionMapper<InvalidParamException> {
    @Override
    public Response toResponse(InvalidParamException e) {
        return Response.status(400)
                .entity(new ErrorResponse(ExceptionType.INVALID_PARAMETER, Dictionary.INVALID_PARAMETER_EXCEPTION_DESC))
                .build();
    }
}
