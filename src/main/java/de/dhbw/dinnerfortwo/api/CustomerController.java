package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Customer.CustomerService;
import de.dhbw.dinnerfortwo.impl.Customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;
import static de.dhbw.dinnerfortwo.api.CustomerController.URI_CUSTOMER_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a restaurant.
 */
@RestController
@RequestMapping(value = URI_CUSTOMER_BASE, produces = "application/json;charset=UTF-8")
public class CustomerController {

    public static final String URI_CUSTOMER_BASE = URI_BASE + "/customers";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable UUID id) {
        log.info("Get customer with id {}", id);
        try {
            var customer = customerService.getCustomer(id);
            return ResponseEntity.ok(customer);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        log.info("Get all customers");
        var result = customerService.getAllCustomers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO newCustomer) {
        Customer customer = new Customer(newCustomer.getName(), newCustomer.getAddress(), newCustomer.getEmail(), newCustomer.getPassword()); // enforce a new ID
        Customer result = customerService.create(customer);
        log.info("Created customer {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing owner, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO newCustomer) {
        Customer updateCustomer = new Customer(id.toString(), newCustomer.getName(), newCustomer.getAddress(), newCustomer.getEmail(), newCustomer.getPassword()); // enforce the id of the parameter ID
        customerService.update(updateCustomer);
        log.info("updated owner {}", updateCustomer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id) {
        customerService.delete(id);
    }

    public static class CustomerDTO {
        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        public CustomerDTO() {
        }

        public CustomerDTO(String name, String address, String email, String password) {
            this.name = name;
            this.address = address;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() { return password; }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }


        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    ", adress='" + address + '\'' +
                    ", email='" + email + '\'' +
                    ", password=" + password + '\'' +
                    '}';
        }
    }
}
