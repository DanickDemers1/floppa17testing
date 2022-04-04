package ulaval.glo2003.application.exceptions;

public enum ExceptionType {
    MISSING_PARAMETER,
    INVALID_PARAMETER,
    ITEM_NOT_FOUND,
    NULL_REFERENCE;

    public String enumToString() {
        return this.toString();
    }
}
