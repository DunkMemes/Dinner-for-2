package de.dhbw.dinnerfortwo.impl.Reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private String customerID;

    @Column(nullable = false)
    private String restaurantID;

    @Column(nullable = false)
    private Date date = new Date();

    public Reservation(int count_seats, String customerID, String restaurantID, Date date) {
        this(UUID.randomUUID().toString(), count_seats, customerID, restaurantID, date);
    }


    public Reservation() {
    }

    public Reservation(String id, int count_seats, String customerID, String restaurantID, Date date) {
        this.reservationID = id;
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


    public String getCustomerID() {
        return customerID;
    }

    public int getCountSeats() {
        return count_seats;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Date getDate() {
        return date;
    }

    public void setId(String id) {
        this.reservationID = id;
    }

    public void setCountSeats(int count_seats) {
        this.count_seats = count_seats;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
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
                "date=" + date +
                '}';
    }
}