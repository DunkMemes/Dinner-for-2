package de.dhbw.dinnerfortwo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue
    private int reservationID;
    private DATETIME date_time;
    private int count_seats;
    private int ownerID;
    private int customerID;
}
