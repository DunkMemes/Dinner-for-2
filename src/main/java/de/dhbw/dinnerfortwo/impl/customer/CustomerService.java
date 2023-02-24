package de.dhbw.dinnerfortwo.impl.Customer;

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
public class CustomerService {
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Customer getCustomerById(UUID id) {
        log.info("Looking for an customer with id {}", id);
        return customerRepository.findById(id.toString()).orElseThrow(() -> new EntityNotFoundException("Could not find Customer with Id " + id));
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll().stream().toList();
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        log.info("Save or update customer {}", customer);
        return customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(Customer customer) {
        var persisted = customerRepository.findById(customer.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find owner with Id " + customer.getId()));
        persisted.setName(customer.getName());
        persisted.setAddress(customer.getAddress());
        persisted.setEmail(customer.getEmail());
        customerRepository.save(customer);
    }

    public void deleteCustomerById(UUID id) {
        log.info("Deleting customer with id {}", id);
        customerRepository.deleteById(id.toString());
    }
}
