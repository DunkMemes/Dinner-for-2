package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantServiceTest(
    @Autowired val repository: RestaurantRepository,
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @AfterEach()
    fun setup() {
        // nothing yet
        println(repository.findAll())
    }

    @Test
    // with back ticks you can quote prosa text as function name
    fun `test rest end point to create a new restaurants`() {
        // prepare test
        val restaurant = newRestaurant()
        val request = post(URI_RESTAURANT_BASE)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(restaurant))

        // execute function under test
        val result = mockMvc
            .perform(request)
            .andExpect(status().isCreated)
            .andReturn()

        // evaluate result
        val createdRestaurant = objectMapper.readValue(result.response.contentAsString, Restaurant::class.java)
        createdRestaurant.name `should be equal to` "Bella Italia"
        createdRestaurant.email `should be equal to` "info@bella.com"
    }

    @Test
    fun `test endpoint to read a restaurant by id`() {
        // prepare database
        val storedRestaurant = newRestaurant()
        val entity = repository.save(storedRestaurant)

        // execute function under test
        val request = get("${URI_RESTAURANT_BASE}/${entity.id}")
            .contentType("application/json")
        val result = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn()

        // evaluate result
        val resultRestaurant = objectMapper.readValue(result.response.contentAsString, Restaurant::class.java)
        resultRestaurant.name `should be equal to` "Bella Italia"
        resultRestaurant.email `should be equal to` "info@bella.com"
    }

    @Test
    fun `test update restaurant`() {
        // prepare database
        val storedRestaurant = newRestaurant()
        val entity = repository.save(storedRestaurant)

        val update = storedRestaurant.copy(rating = 4.5) // copy of the object but with modified rating
        val request = put("${URI_RESTAURANT_BASE}/${entity.id}")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(update))
        mockMvc
            .perform(request)
//            .andDo { print(it) } // just for debugging
            .andExpect(status().isOk) // validate return value
            .andReturn()

        // validate result
        val updatedRestaurant = repository.findById(entity.id).get()
        updatedRestaurant.name `should be equal to` "Bella Italia"
        updatedRestaurant.rating `should be equal to` 4.5
    }

    @Test
    fun `error handling during update restaurant`() {
        // prepare database
        val entity = newRestaurant()

        val request = put("${URI_RESTAURANT_BASE}/${entity.id}")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(entity))
        mockMvc
            .perform(request)
//            .andDo { print(it) } // just for debugging
            .andExpect(status().isNotFound) // validate return value
            .andReturn()
    }

    @Test
    fun `test delete restaurant`() {
        // prepare database
        val storedRestaurant = newRestaurant()
        val entity = repository.save(storedRestaurant)

        //execute function under test
        val request = delete("${URI_RESTAURANT_BASE}/${entity.id}")
        mockMvc
            .perform(request)
            .andExpect(status().isOk) // validate response

        // validate entity has been deleted
        val result = repository.findById(entity.id)
        result.isEmpty `should be equal to` true
    }

    private fun newRestaurant(): Restaurant {
        return Restaurant(name = "Bella Italia", cuisine = "Italy", email = "info@bella.com", rating = 3.0)
    }
}