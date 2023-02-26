package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.ReservationController
import de.dhbw.dinnerfortwo.impl.Reservation.Reservation
import de.dhbw.dinnerfortwo.impl.Reservation.ReservationRepository
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest(@Autowired val repository: ReservationRepository) {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    //val currentDate: LocalDateTime = LocalDateTime(LocalDate.of(1970, 1, 1), LocalTime(23, 59, 59, 999_999_999))

    @BeforeEach()
    fun setup() {
    }
    /*
        @Test
        // with back ticks you can quote prosa text as function name
        fun `test rest end point to create a new Reservation`() {
            // prepare test
            val reservation = newReservation()
            println(reservation.toString())
            println(objectMapper.writeValueAsString(reservation))
            val request = post(ReservationController.URI_RESERVATION_BASE)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(reservation))

            // execute function under test
            val result = mockMvc
                    .perform(request)
                    .andExpect(status().isCreated)
                    .andReturn()

            // evaluate result
            val createdReservation = objectMapper.readValue(result.response.contentAsString, reservation::class.java)
            //createdReservation.date `should be equal to` currentDate
            createdReservation.restaurantID `should be equal to` "ExampleRestaurantID"
            createdReservation.customerID `should be equal to` "ExampleCustomerID"
            createdReservation.countSeats `should be equal to` 4
        }

        @Test
        fun `test endpoint to read a Reservation by id`() {
            // prepare database
            val storedReservation = newReservation()
            val entity = repository.save(storedReservation)

            // execute function under test
            val request = get("${ReservationController.URI_RESERVATION_BASE}/${entity.id}")
                    .contentType("application/json")
            val result = mockMvc.perform(request)
                    .andExpect(status().isOk)
                    .andReturn()

            // evaluate result
            val resultCustomer = objectMapper.readValue(result.response.contentAsString, Reservation::class.java)
           // resultCustomer.date `should be equal to` currentDate
            resultCustomer.restaurantID `should be equal to` "ExampleRestaurantID"
            resultCustomer.customerID `should be equal to` "ExampleCustomerID"
            resultCustomer.countSeats `should be equal to` 4
        }

        @Test
        fun `test update Reservation`() {
            // prepare database
            val storedReservation = newReservation()
            val entity = repository.save(storedReservation)

            val update = ReservationController.ReservationDTO(5,"ExampleCustomerID", "ExampleRestaurantID",currentDate)
            val request = put("${ReservationController.URI_RESERVATION_BASE}/${entity.id}")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(update))
            mockMvc
                    .perform(request)
                    //            .andDo { print(it) } // just for debugging
                    .andExpect(status().isOk) // validate return value
                    .andReturn()

            // validate result
            val updatedReservation = repository.findById(entity.id).get()
            updatedReservation.date `should be equal to` currentDate
            updatedReservation.restaurantID `should be equal to` "ExampleRestaurantID"
            updatedReservation.customerID `should be equal to` "ExampleCustomerID"
            updatedReservation.countSeats `should be equal to` 5
        }


    @Test
    fun `test delete Reservation`() {
        // prepare database
        val storedReservation = newReservation()
        val entity = repository.save(storedReservation)

        //execute function under test
        val request = delete("${ReservationController.URI_RESERVATION_BASE}/${entity.id}")
        mockMvc
                .perform(request)
                .andExpect(status().isOk) // validate response

        // validate entity has been deleted
        val result = repository.findById(entity.id)
        result.isEmpty `should be equal to` true
    }

    private fun newReservation(): Reservation {
        return Reservation(4,"ExampleCustomerID", "ExampleRestaurantID", currentDate)
    }
     */
}