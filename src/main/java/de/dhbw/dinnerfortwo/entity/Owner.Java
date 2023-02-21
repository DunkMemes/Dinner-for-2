package de.dhbw.dinnerfortwo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Owner")
public class Owner {
    @Id
    @GeneratedValue
    private int ownerID;
    private String name;
    private String adress;
    private String email;
    private String password;
}