package ulaval.glo2003.entities.buyer;

import ulaval.glo2003.entities.Entity;

import java.util.UUID;

public class Buyer extends Entity {
    private final UUID id;
    private String name;
    private String email;
    private String phoneNumber;

    public Buyer(String name, String email, String phoneNumber) {
        id = java.util.UUID.randomUUID();
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public Buyer(UUID id, String name, String email, String phoneNumber) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }
    public UUID getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
