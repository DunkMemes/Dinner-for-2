package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Customer.Customer;
import de.dhbw.dinnerfortwo.impl.Customer.CustomerService;
import de.dhbw.dinnerfortwo.impl.Reservation.Reservation;
import de.dhbw.dinnerfortwo.impl.Reservation.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.ReservationController.URI_RESERVATION_BASE;
import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a restaurant.
 */
@RestController
@RequestMapping(value = URI_RESERVATION_BASE, produces = "application/json;charset=UTF-8")
public class ReservationController {

    public static final String URI_RESERVATION_BASE = URI_BASE + "/reservations";

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable UUID id) {
        log.info("Get reservation with id {}", id);
        try {
            var reservation = reservationService.getReservation(id);
            return ResponseEntity.ok(reservation);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        log.info("Get all reservations");
        var result = reservationService.getAllReservations();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO newReservation) {
        Reservation reservation = new Reservation(newReservation.getCountSeats(), newReservation.getCustomerID(), newReservation.getRestaurantID(), newReservation.getDate()); // enforce a new ID
        Reservation result = reservationService.create(reservation);
        log.info("Created customer {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing owner, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateReservation(@PathVariable UUID id, @RequestBody ReservationDTO newReservation) {
        Reservation updateReservation = new Reservation(id.toString(), newReservation.getCountSeats(), newReservation.getCustomerID(), newReservation.getRestaurantID(),newReservation.getDate()); // enforce the id of the parameter ID
        reservationService.update(updateReservation);
        log.info("updated owner {}", updateReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id) {
        reservationService.delete(id);
    }

    public static class ReservationDTO {

        @Column(nullable = false)
        private int count_seats;

        @Column(nullable = false)
        private String customerID;

        @Column(nullable = false)
        private String restaurantID;

        @Column(nullable = false)
        private LocalDateTime date ;

        public ReservationDTO() {
        }

        public ReservationDTO( int count_seats, String customerID, String restaurantID, LocalDateTime date) {
            this.count_seats = count_seats;
            this.customerID = customerID;
            this.restaurantID = restaurantID;
            this.date = date;
        }

        public int getCountSeats() {
            return count_seats;
        }

        public String getCustomerID() {
            return customerID;
        }

        public String getRestaurantID() {
            return restaurantID;
        }

        public LocalDateTime getDate() { return  date; }


        public void setCount_seats(int count_seats) { this.count_seats = count_seats; }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        public void setRestaurantID(String restaurantID) {
            this.restaurantID = restaurantID;
        }

        public void setDate(LocalDateTime date) {this.date = date; }
        @Override
        public String toString() {
            return "Customer{" +
                    "count_seats='" + count_seats + '\'' +
                    ", customerID=" + customerID + '\'' +
                    ", restaurantID='" + restaurantID + '\'' +
                    '}';
        }
    }
}
