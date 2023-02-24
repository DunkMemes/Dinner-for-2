package de.dhbw.dinnerfortwo.impl.Owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Owner")
public class Owner {
    @Id
    //@GeneratedValue
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    public Owner(String name, String address, String email) {
        this(UUID.randomUUID().toString(), name, address, email);
    }

    public Owner() {
    }

    public Owner(String id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        return id.equals(owner.id);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}