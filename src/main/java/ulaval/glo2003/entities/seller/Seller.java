package ulaval.glo2003.entities.seller;

import ulaval.glo2003.entities.Entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

public class Seller extends Entity {
    private final UUID id;
    private String name;
    private String bio;
    private Date birthDate;
    private final OffsetDateTime createdAt;

    public Seller(String name, String bio, Date birthDate) {
        id = java.util.UUID.randomUUID();
        createdAt = Instant.now().atOffset(ZoneOffset.UTC);
        setName(name);
        setBio(bio);
        setBirthDate(birthDate);
    }

    public Seller(UUID id, OffsetDateTime createdAt, String name, String bio, Date birthDate) {
        this.id = id;
        this.createdAt = createdAt;
        setName(name);
        setBio(bio);
        setBirthDate(birthDate);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
