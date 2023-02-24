package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.customer.Customer;
import de.dhbw.dinnerfortwo.impl.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;

import static de.dhbw.dinnerfortwo.api.CustomerController.URI_CUSTOMER_BASE;
import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;

@RestController
@RequestMapping(value = URI_CUSTOMER_BASE)
public class CustomerController {
    public static final String URI_CUSTOMER_BASE = URI_BASE+"/customers";

    private final CustomerService customerService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) throws Throwable {
        log.info("Get Method called to get Customer via ID");
        Customer result = customerService.getCustomerById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        log.info("Get Method called to get all Customers");
        List<Customer> result = customerService.getAllCustomers();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDTO newCustomer){
        Customer customer = new Customer(newCustomer.getName(),newCustomer.getAddress(), newCustomer.getEmail(), newCustomer.getPassword());
        log.info("Creating new Customer with ID: "+ customer.getId());
        Customer result = customerService.createCustomer(customer);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDTO updateCustomer, @PathVariable String id){
        Customer customer = new Customer(id.toString(),updateCustomer.getName(),updateCustomer.getAddress(), updateCustomer.getEmail(),updateCustomer.getPassword());
        Customer result = customerService.updateCustomer(customer);
        log.info("Updated customer{}",customer);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable String id){
        log.info("Delete Method called for customer with ID: {}",id);
        customerService.deleteCustomerBiId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static class CustomerDTO{
        @Column (nullable = false)
        private String name;
        @Column(nullable = false)
        private String address;
        @Column(nullable = false)
        private String password;
        @Column(nullable = false)
        private String email;

        public CustomerDTO(){
        }

        public CustomerDTO(String name, String address, String password, String email) {
            this.name = name;
            this.address = address;
            this.password = password;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "CustomerDTO{" +
                    "name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", password='" + password + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
