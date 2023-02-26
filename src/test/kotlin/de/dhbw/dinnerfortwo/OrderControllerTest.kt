package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.OrderController
import de.dhbw.dinnerfortwo.impl.Order.Order
import de.dhbw.dinnerfortwo.impl.Order.OrderRepository
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest(@Autowired val repository: OrderRepository) {

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
    fun `test rest end point to create a new Order`() {
        // prepare test
        val order = newOrder()
        val request = post(OrderController.URI_ORDER_BASE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(order))

        // execute function under test
        val result = mockMvc
                .perform(request)
                .andExpect(status().isCreated)
                .andReturn()

        // evaluate result
        val createdOrder = objectMapper.readValue(result.response.contentAsString, Order::class.java)
        createdOrder.orderDate `should be equal to` Date()
        createdOrder.customerID `should be equal to` "d36662f3-b068-4866-99ce-77126a22affb"
        createdOrder.restaurantID `should be equal to` "d36662f3-b068-4866-99ce-77126a22affb"
        createdOrder.totalPrice `should be equal to` 30.60
    }

    /*
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
    */
    private fun newOrder(): Order {
        println(Date())
        return Order(Date(), "d36662f3-b068-4866-99ce-77126a22affb", "d36662f3-b068-4866-99ce-77126a22affb", 30.60)
    }
}