package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.RatingController
import de.dhbw.dinnerfortwo.impl.Rating.Rating
import de.dhbw.dinnerfortwo.impl.Rating.RatingRepository
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
class RatingControllerTest(@Autowired val repository: RatingRepository) {

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
    fun `test rest end point to create a new Rating`() {
        // prepare test
        val Rating = newRating()
        val request = post(RatingController.URI_RATING_BASE)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(Rating))

        // execute function under test
        val result = mockMvc
            .perform(request)
            .andExpect(status().isCreated)
            .andReturn()

        // evaluate result
        val createdRating = objectMapper.readValue(result.response.contentAsString, Rating::class.java)
        createdRating.ratingText `should be equal to` "Loved the restaurant"
    }

    @Test
    fun `test endpoint to read a Rating by id`() {
        // prepare database
        val storedRating = newRating()
        val entity = repository.save(storedRating)

        // execute function under test
        val request = get("${RatingController.URI_RATING_BASE}/${entity.id}")
            .contentType("application/json")
        val result = mockMvc.perform(request)
            .andExpect(status().isOk)
            .andReturn()

        // evaluate result
        val resultRating = objectMapper.readValue(result.response.contentAsString, Rating::class.java)
        resultRating.ratingText `should be equal to` "Loved the restaurant"
    }

        @Test
        fun `test update Rating`() {
            // prepare database
            val storedRating = newRating()
            val entity = repository.save(storedRating)

            val update = RatingController.RatingDTO("Updated rating Text")
            val request = put("${RatingController.URI_RATING_BASE}/${entity.id}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(update))
            mockMvc
                .perform(request)
    //            .andDo { print(it) } // just for debugging
                .andExpect(status().isOk) // validate return value
                .andReturn()

            // validate result
            val updatedRating = repository.findById(entity.id).get()
            updatedRating.ratingText `should be equal to` "Updated rating Text"
        }


        @Test
        fun `test delete Rating`() {
            // prepare database
            val storedRating = newRating()
            val entity = repository.save(storedRating)

            //execute function under test
            val request = delete("${RatingController.URI_RATING_BASE}/${entity.id}")
            mockMvc
                .perform(request)
                .andExpect(status().isOk) // validate response

            // validate entity has been deleted
            val result = repository.findById(entity.id)
            result.isEmpty `should be equal to` true
        }

    private fun newRating(): Rating {
        return Rating("Loved the restaurant")
    }
}