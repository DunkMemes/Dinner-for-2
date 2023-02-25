package de.dhbw.dinnerfortwo.impl.Rating;

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
public class RatingService {
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    private final RatingRepository ratingRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Rating getRatingById(UUID id) {
        log.info("Looking for an rating with id {}", id);
        return ratingRepository.findById(id.toString()).orElseThrow(() -> new EntityNotFoundException("Could not find rating with Id " + id));
    }

    @Transactional
    public List<Rating> getAllRatings() {
        log.info("Get all ratings");
        return ratingRepository.findAll().stream().toList();
    }

    @Transactional
    public Rating createRating(Rating rating) {
        log.info("Save or update rating {}", rating);
        return ratingRepository.save(rating);
    }

    @Transactional
    public void updateRating(Rating rating) {
        var persisted = ratingRepository.findById(rating.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find meal with Id " + rating.getId()));
        persisted.setRatingText(rating.getRatingText());
        ratingRepository.save(rating);
    }

    public void deleteRatingById(UUID id) {
        log.info("Deleting rating with id {}", id);
        ratingRepository.deleteById(id.toString());
    }
}
