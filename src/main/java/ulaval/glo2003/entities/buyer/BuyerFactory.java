package ulaval.glo2003.entities.buyer;

import ulaval.glo2003.entities.utils.StringUtils;
import ulaval.glo2003.application.exceptions.InvalidParamException;

public class BuyerFactory {
    private final int PHONE_LENGTH = 11;

    public Buyer createBuyer(String name, String email, String phoneNumber) {
        var isValidEmail = StringUtils.isValidEmail(email);
        var formattedPhoneNumber = StringUtils.removeAllNonNumericCharacters(phoneNumber);
        var isValidPhoneNumber = formattedPhoneNumber.length() == PHONE_LENGTH;

        if (name.isBlank() || !isValidPhoneNumber || !isValidEmail)
            throw new InvalidParamException();

        return new Buyer(name, email, phoneNumber);
    }
}
