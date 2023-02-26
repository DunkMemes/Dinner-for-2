package de.dhbw.dinnerfortwo.impl.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "OrderTable")
public class Order{
    @Id
    private String orderID;

    @Column(nullable = false, name = "orderDate" )
    private Date orderDate;

    @Column(nullable = false, name = "restaurantID")
    private String  restaurantID;

    @Column(nullable = false, name = "customerID" )
    private String customerID;

    @Column(nullable = false, name = "totalPrice")
    private double totalPrice;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public Order() {
    }
    public Order(String orderID, Date orderDate, String restaurantID, String customerID, double totalPrice) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }

    public Order(Date orderDate, String restaurantID, String customerID, double totalPrice) {
        this.orderID = UUID.randomUUID().toString();
        this.orderDate = orderDate;
        this.restaurantID = restaurantID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

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
                ", orderDate=" + orderDate +
                ", restarauntID='" + restaurantID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
