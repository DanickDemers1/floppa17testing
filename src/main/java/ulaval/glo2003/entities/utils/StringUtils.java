package ulaval.glo2003.entities.utils;

import ulaval.glo2003.application.exceptions.InvalidParamException;
import org.apache.commons.validator.routines.EmailValidator;

public class StringUtils {
    public static Float parseStringToFloat(String numberString) {
        try {
            return Float.parseFloat(numberString);
        } catch (RuntimeException e) {
            throw new InvalidParamException();
        }
    }

    public static Boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public static String removeAllNonNumericCharacters(String phoneNumber) {
        return phoneNumber.replaceAll("[^\\d]", "");
    }
}
