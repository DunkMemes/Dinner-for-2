package de.dhbw.dinnerfortwo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Restaurant")
public class Restaurant {
    @Id
    @GeneratedValue
    private int restaurantID;
    private String name;
    private String cuisine;
    private int ownerID;
    private float rating;
    private int tables;
    private String menu;
}
