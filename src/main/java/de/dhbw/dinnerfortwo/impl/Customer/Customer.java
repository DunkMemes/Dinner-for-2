package de.dhbw.dinnerfortwo.impl.Customer;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue
    private String customerID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Customer(String name, String address, String email, String password) {
        this(UUID.randomUUID().toString(), name, address, email, password);
    }

    public Customer() {
    }

    public Customer(String id, String name, String address, String email, String password) {
        this.customerID = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return customerID;
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
        this.customerID = id;
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

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return customerID.equals(customer.customerID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return customerID.hashCode();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + customerID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
