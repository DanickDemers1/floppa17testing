package ulaval.glo2003.api.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import ulaval.glo2003.application.exceptions.ExceptionType;
import ulaval.glo2003.api.exceptions.Dictionary;
import ulaval.glo2003.api.exceptions.ErrorResponse;
import ulaval.glo2003.application.exceptions.MissingParamException;

public class MissingParamExceptionMapper implements ExceptionMapper<MissingParamException> {
    @Override
    public Response toResponse(MissingParamException e) {
        return Response.status(400)
                .entity(new ErrorResponse(ExceptionType.MISSING_PARAMETER, Dictionary.MISSING_PARAMETER_EXCEPTION_DESC))
                .build();
    }
}
