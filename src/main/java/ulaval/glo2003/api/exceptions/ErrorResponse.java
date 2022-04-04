package ulaval.glo2003.api.exceptions;

import ulaval.glo2003.application.exceptions.ExceptionType;

public class ErrorResponse {
    public ExceptionType code;
    public String description;

    public ErrorResponse(ExceptionType errorCode, String description) {
        this.code = errorCode;
        this.description = description;
    }
}
