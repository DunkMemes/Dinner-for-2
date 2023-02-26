package de.dhbw.dinnerfortwo.impl.Rating;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Rating")
public class Rating extends Object {
    @Id
    private String ratingID;
    @Column(nullable = false)
    private String ratingText;


    public Rating() {
    }

    public Rating(String id, String text) {
        this.ratingID = id;
        this.ratingText = text;
    }

    public Rating(String text)  {
        this.ratingID = UUID.randomUUID().toString();
        this.ratingText = text;
    }

    public String getId() {
        return ratingID;
    }
    public String getRatingText() {
        return ratingText;
    }

    public void setId(String id) {
        this.ratingID = id;
    }
    public void setRatingText(String text) {
        this.ratingText = text;
    }


    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        return ratingID.equals(rating.ratingID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return ratingID.hashCode();
    }

    @Override
    public String toString() {
        return "Rating{" +
                "RatingID='" + ratingID + '\'' +
                ", Text='" + ratingText + '\'' +
                '}';
    }
}
