package de.dhbw.dinnerfortwo.impl.restaurant;

import com.fasterxml.jackson.module.kotlin.ReflectionCache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Restaurant {
    @Id
    private String restaurantID;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String cuisine;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Double rating;

    public Restaurant() {
    }

    public Restaurant(String restaurantID, String name, String cuisine, String email, Double rating) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.cuisine = cuisine;
        this.email = email;
        this.rating = rating;
    }

    public Restaurant(String name, String cuisine, String email, Double rating) {
        this.restaurantID = UUID.randomUUID().toString();
        this.name = name;
        this.cuisine = cuisine;
        this.email = email;
        this.rating = rating;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID=" + restaurantID +
                ", name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", email='" + email + '\'' +
                ", rating=" + rating +
                '}';
    }
}
