package ulaval.glo2003.entities.seller;

import ulaval.glo2003.api.utils.DateUtils;
import ulaval.glo2003.application.exceptions.InvalidParamException;

public class SellerFactory {
    public Seller createSeller(String name, String bio, String birthDate) {
        var formattedBirthDate = DateUtils.parseStringToDate(birthDate);
        var timeSinceBirth = DateUtils.getElapsedTimeSince(formattedBirthDate);
        if (timeSinceBirth.getYears() < 18 || bio.isBlank() || name.isBlank())
            throw new InvalidParamException();

        return new Seller(name, bio, formattedBirthDate);
    }
}
