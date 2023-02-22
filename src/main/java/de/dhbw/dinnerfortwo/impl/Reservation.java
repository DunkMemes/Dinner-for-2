package de.dhbw.dinnerfortwo.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    //@GeneratedValue
    private String reservationID;

    @Column(nullable = false)
    private int count_seats;

    @Column(nullable = false)
    private int customerID;

    @Column(nullable = false)
    private int restaurantID;

    @Column(nullable = false)
    private Date date = new Date();

    public Reservation() {
    }

    public Reservation(String id) {
        this.reservationID = id;
    }

    public String getId() {
        return reservationID;
    }

    public void setId(String id) {
        this.reservationID = id;
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object r) {
        if (this == r) return true;
        if (r == null || getClass() != r.getClass()) return false;

        Reservation reservation = (Reservation) r;

        return reservationID.equals(reservation.reservationID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return reservationID.hashCode();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + reservationID +
                "date=" + date +
                "customerID=" + customerID +
                "restaurantID=" + restaurantID +
                '}';
    }
}