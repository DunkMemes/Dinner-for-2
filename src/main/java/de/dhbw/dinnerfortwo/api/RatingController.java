package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Rating.Rating;
import de.dhbw.dinnerfortwo.impl.Rating.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.RatingController.URI_RATING_BASE;
import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a meal.
 */
@RestController
@RequestMapping(value = URI_RATING_BASE, produces = "application/json;charset=UTF-8")
public class RatingController {

    public static final String URI_RATING_BASE = URI_BASE + "/ratings";

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<de.dhbw.dinnerfortwo.impl.Rating.Rating> getRating(@PathVariable UUID id) {
        log.info("Get rating with id {}", id);
        try {
            var rating = ratingService.getRatingById(id);
            return ResponseEntity.ok(rating);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        log.info("Get all ratings");
        var result = ratingService.getAllRatings();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingDTO newRating) {
        Rating rating = new Rating(newRating.getRatingText()); // enforce a new ID
        Rating result = ratingService.createRating(rating);
        log.info("Created rating {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing rating, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRating(@PathVariable UUID id, @RequestBody RatingDTO newRating) {
        Rating updateRating = new Rating(id.toString(), newRating.getRatingText()); // enforce the id of the parameter ID
        ratingService.updateRating(updateRating);
        log.info("updated rating {}", updateRating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable UUID id) {
        ratingService.deleteRatingById(id);
    }

    public static class RatingDTO{
        @Column
        private String ratingText;

        public String getRatingText() {
            return ratingText;
        }

        public void setRatingText(String ratingText) {
            this.ratingText = ratingText;
        }

        public RatingDTO() {
        }

        public RatingDTO(String ratingText) {
            this.ratingText = ratingText;
        }

        @Override
        public String toString() {
            return "RatingDTO{" +
                    "ratingText='" + ratingText + '\'' +
                    '}';
        }
    }
}
