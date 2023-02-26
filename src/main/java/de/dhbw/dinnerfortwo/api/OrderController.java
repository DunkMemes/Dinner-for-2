package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Order.Order;
import de.dhbw.dinnerfortwo.impl.Order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;
import static de.dhbw.dinnerfortwo.api.OrderController.URI_ORDER_BASE;

@RestController
@RequestMapping(value = URI_ORDER_BASE, produces = "application/json;charset=UTF-8")
public class OrderController {
    public static final String URI_ORDER_BASE = URI_BASE + "/orders";

    private final OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable String id) {
        log.info("Get Method called. Get order with ID: " + id);
        try {
            return orderService.getOrderById(id);
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Get all orders");
        var result = orderService.getAllOrders();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO newOrder) {
        Order order = new Order(newOrder.getDate(),  newOrder.getRestaurantID(), newOrder.getCustomerID(),newOrder.getTotalPrice());
        System.out.println(newOrder.toString());
        System.out.println(order.toString());
        Order result = orderService.createOrder(order);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateOrder(@PathVariable UUID id, @RequestBody OrderDTO updateOrder){
        Order order = new Order(id.toString(),updateOrder.getDate(), updateOrder.getCustomerID(), updateOrder.getRestaurantID(), updateOrder.getTotalPrice());
        try {
            orderService.updateOrder(order);
            log.info("Updating order with ID: " + id.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable UUID id){
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public static class OrderDTO{
        @Column(nullable = false)
        private Date date;

        @Column(nullable = false)
        private String  restaurantID;

        @Column(nullable = false)
        private String customerID;

        @Column(nullable = false)
        private double totalPrice;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
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

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public OrderDTO() {
        }

        public OrderDTO(Date date, String restaurantID, String customerID, double totalPrice) {
            this.date = date;
            this.restaurantID = restaurantID;
            this.customerID = customerID;
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "OrderDTO{" +
                    "date=" + date +
                    ", restaurantID='" + restaurantID + '\'' +
                    ", customerID='" + customerID + '\'' +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }




}
