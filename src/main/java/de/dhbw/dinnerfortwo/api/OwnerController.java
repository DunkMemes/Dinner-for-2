package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.owner.Owner;
import de.dhbw.dinnerfortwo.impl.owner.OwnerService;
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
import static de.dhbw.dinnerfortwo.api.OwnerController.URI_OWNER_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a restaurant.
 */
//Das ist ein Testkommentar
@RestController
@RequestMapping(value = URI_OWNER_BASE, produces = "application/json;charset=UTF-8")
public class OwnerController {

    public static final String URI_OWNER_BASE = URI_BASE + "/owners";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable UUID id) {
        log.info("Get owner with id {}", id);
        try {
            var owner = ownerService.getOwner(id);
            return ResponseEntity.ok(owner);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        log.info("Get all owners");
        var result = ownerService.getAllOwners();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody OwnerDTO newOwner) {
        Owner owner = new Owner(newOwner.getName(), newOwner.getAddress(), newOwner.getEmail()); // enforce a new ID
        Owner result = ownerService.create(owner);
        log.info("Created owner {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing owner, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateOwner(@PathVariable UUID id, @RequestBody OwnerDTO owner) {
        Owner updateOwner = new Owner(id.toString(), owner.getName(), owner.getAddress(), owner.getEmail()); // enforce the id of the parameter ID
        ownerService.update(updateOwner);
        log.info("updated owner {}", updateOwner);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable UUID id) {
        ownerService.delete(id);
    }

    public static class OwnerDTO {
        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String address;

        @Column(nullable = false)
        private String email;

        public OwnerDTO() {
        }

        public OwnerDTO(String name, String address, String email) {
            this.name = name;
            this.address = address;
            this.email = email;
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

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        @Override
        public String toString() {
            return "Owner{" +
                    "name='" + name + '\'' +
                    ", adress='" + address + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
