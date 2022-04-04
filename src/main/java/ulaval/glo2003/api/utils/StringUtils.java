package ulaval.glo2003.api.utils;

import ulaval.glo2003.application.exceptions.ItemNotFoundException;

import java.util.UUID;

public class StringUtils {
    public static UUID parseStringToUUID(String stringId) {
        try {
            return UUID.fromString(stringId);
        } catch (RuntimeException e) {
            throw new ItemNotFoundException();
        }
    }
}
