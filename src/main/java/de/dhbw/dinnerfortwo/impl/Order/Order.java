package de.dhbw.dinnerfortwo.impl.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Order")
public class Order extends Object {
    @Id
    private String orderID;

    @Column(nullable = false)
    private Date date = new Date();

    @Column(nullable = false)
    private String  restaurantID;

    @Column(nullable = false)
    private String customerID;

    @Column(nullable = false)
    private double totalPrice;

    public Order() {
    }

    public Order(String id, Date date, String restaurantID, String customerID, double totalPrice) {
        this.orderID = id;
        this.date = date;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }

    public Order(Date date, String restaurantID, String customerID, double totalPrice) {
        this.orderID = UUID.randomUUID().toString();
        this.date = date;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return orderID;
    }

    public Date getDate() {
        return date;
    }

    public String getRestaurantId() {
        return restaurantID;
    }

    public String getCustomerId() {
        return customerID;
    }

    public double getTotalPrice() { return totalPrice; }

    public void setId(String id) {
        this.orderID = id;
    }

    public void setDate(Date date) {
            this.date = date;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }


    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return orderID.equals(order.orderID);
    }

    // equals and hash code must be based on the ID for JPA to work well.
    @Override
    public int hashCode() {
        return customerID.hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", date=" + date +
                ", restarauntID='" + restaurantID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
