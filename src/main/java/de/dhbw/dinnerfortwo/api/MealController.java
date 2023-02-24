package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Meal.Meal;
import de.dhbw.dinnerfortwo.impl.Meal.MealService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;
import static de.dhbw.dinnerfortwo.api.MealController.URI_OWNER_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a meal.
 */
@RestController
@RequestMapping(value = URI_OWNER_BASE, produces = "application/json;charset=UTF-8")
public class MealController {

    public static final String URI_OWNER_BASE = URI_BASE + "/meals";

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMeal(@PathVariable UUID id) {
        log.info("Get meal with id {}", id);
        try {
            var meal = mealService.getMealById(id);
            return ResponseEntity.ok(meal);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        log.info("Get all meals");
        var result = mealService.getAllMeals();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody MealDTO newMeal) {
        Meal meal = new Meal(newMeal.getName(), newMeal.getPrice(), newMeal.getCategory()); // enforce a new ID
        Meal result = mealService.createMeal(meal);
        log.info("Created meal {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing meal, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateMeal(@PathVariable UUID id, @RequestBody MealDTO meal) {
        Meal updateMeal = new Meal(id.toString(), meal.getName(), meal.getPrice(), meal.getCategory()); // enforce the id of the parameter ID
        mealService.updateMeal(updateMeal);
        log.info("updated meal {}", updateMeal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable UUID id) {
        mealService.deleteMealById(id);
    }

    public static class MealDTO {
        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private double price;

        @Column(nullable = false)
        private String category;

        public MealDTO() {
        }

        public MealDTO(String name, double price, String category) {
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return getPrice();
        }

        public String getCategory() {
            return category;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "MealDTO{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
}
