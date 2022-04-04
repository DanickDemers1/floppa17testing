package ulaval.glo2003.entities.offer;

import ulaval.glo2003.application.exceptions.InvalidParamException;

import java.util.UUID;

public class OfferFactory {
    private final int MIN_MESSAGE_LENGTH = 100;

    public Offer createOffer(UUID productId, UUID buyerId, Float amount, String message) {
        if (message.length() < MIN_MESSAGE_LENGTH)
            throw new InvalidParamException();

        return new Offer(productId, buyerId, amount, message);
    }
}
