package ulaval.glo2003.api.requests;

public class CreateOfferRequest implements Request {
    public String name;
    public String email;
    public String phoneNumber;
    public Float amount;
    public String message;

    public CreateOfferRequest() {
    }

    public CreateOfferRequest(String name, String email, String phoneNumber, Float amount, String message) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.message = message;
    }

    public Boolean allParametersAreDefined() {
        return name != null && email != null && phoneNumber != null && amount != null && message != null;
    }
}
