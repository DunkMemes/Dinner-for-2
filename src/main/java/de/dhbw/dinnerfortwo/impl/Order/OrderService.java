package de.dhbw.dinnerfortwo.impl.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * The CustomerService contains the operations related to managing Customers.
 */
@Service
public class OrderService {
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private final OrderRepository orderRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Order getOrderById(String id) {
        log.info("Looking for an order with id {}", id);
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find Order with Id " + id));
    }

    @Transactional
    public List<Order> getAllOrders() {
        log.info("Get all orders");
        return orderRepository.findAll().stream().toList();
    }

    @Transactional
    public Order createOrder(Order order) {
        log.info("Save or update order {}", order);
        return orderRepository.save(order);
    }

    @Transactional
    public void updateOrder(Order order) {
        var persisted = orderRepository.findById(order.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find Order with Id " + order.getId()));
        persisted.setCustomerID(order.getCustomerId());
        persisted.setDate(order.getDate());
        persisted.setRestaurantID(order.getRestaurantId());
        persisted.setTotalPrice(order.getTotalPrice());
        orderRepository.save(order);
    }

    public void deleteOrderById(UUID id) {
        log.info("Deleting order with id {}", id);
        orderRepository.deleteById(id.toString());
    }
}
