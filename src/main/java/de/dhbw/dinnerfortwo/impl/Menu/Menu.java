package de.dhbw.dinnerfortwo.impl.Menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Menu")
public class Menu extends Object {
    @Id
    private String menuID;
    @Column(nullable = false)
    private String category;

    public Menu() {
    }

    public Menu(String id, String category) {
        this.menuID = id;
        this.category = category;
    }

    public Menu(String category)  {
        this.menuID = UUID.randomUUID().toString();
        this.category = category;
    }

    public String getId() {
        return menuID;
    }
    public String getCategory() {
        return category;
    }

    public void setId(String id) {
        this.menuID = id;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu meal = (Menu) o;

        return menuID.equals(meal.menuID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return menuID.hashCode();
    }

    @Override
    public String toString() {
        return "Menu{" +
                "MenuID='" + menuID + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
