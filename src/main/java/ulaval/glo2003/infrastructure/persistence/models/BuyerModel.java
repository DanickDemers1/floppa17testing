package ulaval.glo2003.infrastructure.persistence.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.UUID;

@Entity("buyers")
public class BuyerModel {
    @Id
    public UUID id;
    public String name;
    public String email;
    public String phoneNumber;
}
