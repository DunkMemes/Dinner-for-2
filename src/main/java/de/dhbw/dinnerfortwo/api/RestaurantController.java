package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.restaurant.Restaurant;
import de.dhbw.dinnerfortwo.impl.restaurant.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.util.List;

import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;
import static de.dhbw.dinnerfortwo.api.RestaurantController.URI_RESTAURANT_BASE;

@RestController
@RequestMapping(value = URI_RESTAURANT_BASE, produces = "application/json;charset=UTF-8")
public class RestaurantController {
    public static final String URI_RESTAURANT_BASE = URI_BASE + "/restaurants";

    private final RestaurantService restaurantService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable String id) {
        log.info("Get Method called. Get Restauarant with ID: " + id);
        try {
            return restaurantService.getRestaurantById(id);
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        log.info("Get all restaurants");
        var result = restaurantService.getAllRestuaurants();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantDTO newRestaurant) {
        Restaurant restaurant = new Restaurant(newRestaurant.getName(), newRestaurant.getCuisine(), newRestaurant.getEmail(), newRestaurant.getRating());
        Restaurant result = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRestaurant(@PathVariable String id, @RequestBody RestaurantDTO updateRestaurant){
        Restaurant restaurant = new Restaurant(id.toString(), updateRestaurant.getName(), updateRestaurant.getCuisine(), updateRestaurant.getEmail(), updateRestaurant.getRating());
        try {
            restaurantService.updateRestaurant(restaurant);
            log.info("Updating restaurant with ID: " + id.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable String id){
        restaurantService.deleteRestaurantById(id.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public static class RestaurantDTO {

        @Column
        private String name;
        @Column
        private  String cuisine;
        @Column
        private Double rating;
        @Column
        private String email;

        public RestaurantDTO(String name, String cuisine, Double rating, String email) {
            this.name = name;
            this.cuisine = cuisine;
            this.rating = rating;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "RestaurantDTO{" +
                    "name='" + name + '\'' +
                    ", cuisine='" + cuisine + '\'' +
                    ", rating=" + rating +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
