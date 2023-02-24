package de.dhbw.dinnerfortwo.impl.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@Service
public class CustomerService {
    private  final CustomerRepository customerRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(UUID Id){
        log.info("Looking for Customer with ID: "+Id);
        return customerRepository.findById(Id.toString()).orElseThrow(()-> new EntityNotFoundException("Customer with ID: "+Id+" not found"));
    }
    public List<Customer> getAllCustomers(){
        log.info("Searching for all Customers");
        return customerRepository.findAll().stream().toList();
    }

    public Customer createCustomer(Customer customer){
        log.info("Creating new Customer");
        return customerRepository.save(customer);
    }

    public Customer updateCustomer( Customer updateCustomer){
        log.info("Updating Customer with ID: "+ updateCustomer.getId());
        var persitent = customerRepository.findById(updateCustomer.getId()).orElseThrow(()-> new EntityNotFoundException("Customer not found"));
        persitent.setAddress(updateCustomer.getAddress());
        persitent.setEmail(updateCustomer.getEmail());
        persitent.setName(updateCustomer.getName());
        persitent.setPassword(updateCustomer.getPassword());
        return  customerRepository.save(persitent);
    }

    public void deleteCustomerBiId(String Id){
        log.info("Deleting Customer with ID: "+ Id);
        customerRepository.deleteById(Id);
    }
}
