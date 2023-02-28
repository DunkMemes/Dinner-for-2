package de.dhbw.dinnerfortwo.impl.Restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private void initDB(){
        restaurantRepository.save(new Restaurant("bfe1168c-9284-4e7c-8827-03f59805d724","Bella Italia","Italian","BellaItalia@Italy.com",4.6));
        restaurantRepository.save(new Restaurant("20c2b59d-ab7c-4268-9b61-ddab7f4ee352","Shanghai","Asian","Shanghai@China.com",4.3));
        count_init++;
    }
    private int count_init = 0;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant getRestaurantById(String ID) {
        log.info("Fetching Restaurant by ID: " + ID);
        return restaurantRepository.findById(ID).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    public List<Restaurant> getAllRestuaurants() {
        if(count_init<1){
            initDB();
        }
        log.info("Fetch all Restaurants");
        return restaurantRepository.findAll().stream().toList();
    }

    public Restaurant createRestaurant(Restaurant newRestaurant) {
        log.info("Creating new Restaurant");
        return restaurantRepository.save(newRestaurant);
    }

    public void updateRestaurant(Restaurant updateRestaurant) {
        log.info("Update Restaurant");
        var persisted = restaurantRepository.findById(updateRestaurant.getRestaurantID()).orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
        persisted.setName(updateRestaurant.getName());
        persisted.setEmail(updateRestaurant.getEmail());
        persisted.setCuisine(updateRestaurant.getCuisine());
        persisted.setRating(updateRestaurant.getRating());
        restaurantRepository.save(persisted);
    }

    public void deleteRestaurantById(String Id) {
        log.info("Delete Restaurant with ID: " + Id);
        restaurantRepository.deleteById(Id.toString());
    }
}
