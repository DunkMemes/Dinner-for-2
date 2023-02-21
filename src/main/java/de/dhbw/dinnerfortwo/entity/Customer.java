package de.dhbw.dinnerfortwo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue
    private int customerID;
    private String name;
    private String email;
    private String password;
}
