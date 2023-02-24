package de.dhbw.dinnerfortwo.impl.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository is responsible to interact with the database.
 * The default JpaRepository provides already all basic CRUD operations like reading or storing an entity.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
