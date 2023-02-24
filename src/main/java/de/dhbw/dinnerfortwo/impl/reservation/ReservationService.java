package de.dhbw.dinnerfortwo.impl.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class ReservationService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public Reservation getReservationById(String id){
            log.info("Get Reservation with ID: "+ id);
            return reservationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Reservation Entity not found"));
    }

    public List<Reservation> getAllReservations(){
        log.info("Get All Reservations");
        return reservationRepository.findAll().stream().toList();
    }

    public Reservation createReservation(Reservation newReservation){
        log.info("Create new Reservation {}", newReservation.toString());
        return reservationRepository.save(newReservation);
    }

    public Reservation updateReservation(Reservation reservation){
        return null;
    }
}
