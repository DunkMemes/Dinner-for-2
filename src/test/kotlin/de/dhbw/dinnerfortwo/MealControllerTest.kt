package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.MealController
import de.dhbw.dinnerfortwo.impl.Meal.Meal
import de.dhbw.dinnerfortwo.impl.Meal.MealRepository
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerTest(@Autowired val repository: MealRepository) {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach()
    fun setup() {
        // nothing yet
    }

    @Test
    // with back ticks you can quote prosa text as function name
    fun `test rest end point to create a new Meal`() {
        // prepare test
        val Meal = newMeal()
        val request = post(MealController.URI_MEAL_BASE)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(Meal))

        // execute function under test
        val result = mockMvc
            .perform(request)
            .andExpect(status().isCreated)
            .andReturn()

        // evaluate result
        val createdMeal = objectMapper.readValue(result.response.contentAsString, Meal::class.java)
        createdMeal.name `should be equal to` "Fries"
        createdMeal.price `should be equal to` 3.90
        createdMeal.category `should be equal to` "Dishes"
    }

    @Test
    fun `test endpoint to read a Meal by id`() {
        // prepare database
        val storedMeal = newMeal()
        val entity = repository.save(storedMeal)

        // execute function under test
        val request = get("${MealController.URI_MEAL_BASE}/${entity.id}")
            .contentType("application/json")
        val result = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn()

        // evaluate result
        val resultMeal = objectMapper.readValue(result.response.contentAsString, Meal::class.java)
        resultMeal.name `should be equal to` "Fries"
        resultMeal.price `should be equal to` 3.90
        resultMeal.category `should be equal to` "Dishes"
    }

            @Test
            fun `test update Meal`() {
                // prepare database
                val storedMeal = newMeal()
                val entity = repository.save(storedMeal)

                val update = MealController.MealDTO("Fries",4.90,"Dishes")
                val request = put("${MealController.URI_MEAL_BASE}/${entity.id}")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(update))
                mockMvc
                    .perform(request)
        //            .andDo { print(it) } // just for debugging
                    .andExpect(status().isOk) // validate return value
                    .andReturn()

                // validate result
                val updatedMeal = repository.findById(entity.id).get()
                updatedMeal.name `should be equal to` "Fries"
                updatedMeal.price `should be equal to` 4.90
                updatedMeal.category `should be equal to` "Dishes"
            }

                @Test
                fun `test delete Meal`() {
                    // prepare database
                    val storedRating = newMeal()
                    val entity = repository.save(storedRating)

                    //execute function under test
                    val request = delete("${MealController.URI_MEAL_BASE}/${entity.id}")
                    mockMvc
                        .perform(request)
                        .andExpect(status().isOk) // validate response

                    // validate entity has been deleted
                    val result = repository.findById(entity.id)
                    result.isEmpty `should be equal to` true
                }

    private fun newMeal(): Meal {
        return Meal("Fries", 3.90, "Dishes")
    }
}