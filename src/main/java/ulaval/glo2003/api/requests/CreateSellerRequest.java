package ulaval.glo2003.api.requests;

public class CreateSellerRequest implements Request {
    public String name;
    public String bio;
    public String birthDate;

    public CreateSellerRequest() {
    }

    public CreateSellerRequest(String name, String bio, String birthDate){
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    public Boolean allParametersAreDefined() {
        return name != null && bio != null && birthDate != null;
    }
}
