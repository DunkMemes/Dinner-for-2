package de.dhbw.dinnerfortwo.impl.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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
    private LocalDateTime date;

    public Reservation() {
    }

    public Reservation(String reservationID, int count_seats, int customerID, int restaurantID, LocalDateTime date) {
        this.reservationID = reservationID;
        this.count_seats = count_seats;
        this.customerID = customerID;
        this.restaurantID = restaurantID;
        this.date = date;
    }

    public Reservation(int count_seats, int customerID, int restaurantID, LocalDateTime date) {
        this.reservationID = UUID.randomUUID().toString();
        this.count_seats = count_seats;
        this.customerID = customerID;
        this.restaurantID = restaurantID;
        this.date = date;
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

    public int getCount_seats() {
        return count_seats;
    }

    public void setCount_seats(int count_seats) {
        this.count_seats = count_seats;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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