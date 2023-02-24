package de.dhbw.dinnerfortwo.impl.Meal;

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
public class MealService {
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    private final MealRepository mealRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Meal getMealById(UUID id) {
        log.info("Looking for an meal with id {}", id);
        return mealRepository.findById(id.toString()).orElseThrow(() -> new EntityNotFoundException("Could not find meal with Id " + id));
    }

    @Transactional
    public List<Meal> getAllMeals() {
        log.info("Get all meals");
        return mealRepository.findAll().stream().toList();
    }

    @Transactional
    public Meal createMeal(Meal meal) {
        log.info("Save or update meal {}", meal);
        return mealRepository.save(meal);
    }

    @Transactional
    public void updateMeal(Meal meal) {
        var persisted = mealRepository.findById(meal.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find meal with Id " + meal.getId()));
        persisted.setName(meal.getName());
        persisted.setPrice(meal.getPrice());
        persisted.setCategory(meal.getCategory());
        mealRepository.save(meal);
    }

    public void deleteMealById(UUID id) {
        log.info("Deleting meal with id {}", id);
        mealRepository.deleteById(id.toString());
    }
}
