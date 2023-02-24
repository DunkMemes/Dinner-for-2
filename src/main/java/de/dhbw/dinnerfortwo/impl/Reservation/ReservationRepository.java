package de.dhbw.dinnerfortwo.impl.Reservation;

import de.dhbw.dinnerfortwo.impl.Reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository is responsible to interact with the database.
 * The default JpaRepository provides already all basic CRUD operations like reading or storing an entity.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
}
