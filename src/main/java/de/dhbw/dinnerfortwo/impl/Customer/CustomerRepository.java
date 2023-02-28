package de.dhbw.dinnerfortwo.impl.Customer;

import de.dhbw.dinnerfortwo.impl.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository is responsible to interact with the database.
 * The default JpaRepository provides already all basic CRUD operations like reading or storing an entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}