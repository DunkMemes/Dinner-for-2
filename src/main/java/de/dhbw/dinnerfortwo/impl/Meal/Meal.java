package de.dhbw.dinnerfortwo.impl.Meal;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Meal")
public class Meal extends Object {
    @Id
    private String mealID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String category;

    public Meal() {
    }

    public Meal(String id, String name, double price, String category) {
        this.mealID = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Meal(String name, double price, String category)  {
        this.mealID = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return mealID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.mealID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        return mealID.equals(meal.mealID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return mealID.hashCode();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealID='" + mealID + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
