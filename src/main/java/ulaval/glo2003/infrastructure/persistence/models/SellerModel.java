package ulaval.glo2003.infrastructure.persistence.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.UUID;

@Entity("sellers")
public class SellerModel {
    @Id
    public UUID id;
    public String name;
    public String createdAt;
    public String bio;
    public String birthDate;
}
