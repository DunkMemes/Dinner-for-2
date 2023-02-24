package de.dhbw.dinnerfortwo.impl.Reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * The OwnerService contains the operations related to managing Owners.
 */
@Service
public class ReservationService {
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private final ReservationRepository reservationRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Reservation getReservation(UUID id) {
        log.info("Looking for an reservation with id {}", id);
        return reservationRepository.findById(id.toString()).orElseThrow(() -> new EntityNotFoundException("Could not find reservation with Id " + id));
    }

    @Transactional
    public List<Reservation> getAllReservations() {
        log.info("Get all reservations");
        return reservationRepository.findAll().stream().toList();
    }

    @Transactional
    public Reservation create(Reservation reservation) {
        log.info("Save or update reservation {}", reservation);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void update(Reservation reservation) {
        var persisted = reservationRepository.findById(reservation.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find reservation with Id " + reservation.getId()));
        persisted.setCountSeats(reservation.getCountSeats());
        persisted.setCustomerID(reservation.getCustomerID());
        persisted.setRestaurantID(reservation.getRestaurantID());
        reservationRepository.save(reservation);
    }

    public void delete(UUID id) {
        log.info("Deleting reservation with id {}", id);
        reservationRepository.deleteById(id.toString());
    }
}
