package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.CustomerController
import de.dhbw.dinnerfortwo.impl.Customer.Customer
import de.dhbw.dinnerfortwo.impl.Customer.CustomerRepository
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
class CustomerControllerTest(@Autowired val repository: CustomerRepository) {

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
    fun `test rest end point to create a new Customer`() {
        // prepare test
        val customer = newCustomer()
        val request = post(CustomerController.URI_CUSTOMER_BASE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(customer))

        // execute function under test
        val result = mockMvc
                .perform(request)
                .andExpect(status().isCreated)
                .andReturn()

        // evaluate result
        val createdCustomer = objectMapper.readValue(result.response.contentAsString, customer::class.java)
        createdCustomer.name `should be equal to` "Jan"
        createdCustomer.address `should be equal to` "testStrasse 65"
        createdCustomer.email `should be equal to` "jan@juergenberger.de"
        createdCustomer.password `should be equal to` "testPassword"
    }

    @Test
    fun `test endpoint to read a Customer by id`() {
        // prepare database
        val storedCustomer = newCustomer()
        val entity = repository.save(storedCustomer)

        // execute function under test
        val request = get("${CustomerController.URI_CUSTOMER_BASE}/${entity.id}")
                .contentType("application/json")
        val result = mockMvc.perform(request)
                .andExpect(status().isOk)
                .andReturn()

        // evaluate result
        val resultCustomer = objectMapper.readValue(result.response.contentAsString, Customer::class.java)
        resultCustomer.name `should be equal to` "Jan"
        resultCustomer.address `should be equal to` "testStrasse 65"
        resultCustomer.email `should be equal to` "jan@juergenberger.de"
        resultCustomer.password `should be equal to` "testPassword"
    }

    @Test
    fun `test update Customer`() {
        // prepare database
        val storedCustomer = newCustomer()
        val entity = repository.save(storedCustomer)

        val update = CustomerController.CustomerDTO("Jan","testStrasse 89", "testPassword", "jan@juergenberger.de")
        val request = put("${CustomerController.URI_CUSTOMER_BASE}/${entity.id}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(update))
        mockMvc
                .perform(request)
                //            .andDo { print(it) } // just for debugging
                .andExpect(status().isOk) // validate return value
                .andReturn()

        // validate result
        val updatedCustomer = repository.findById(entity.id).get()
        updatedCustomer.name `should be equal to` "Jan"
        updatedCustomer.address `should be equal to` "testStrasse 89"
        updatedCustomer.email `should be equal to` "jan@juergenberger.de"
        updatedCustomer.password `should be equal to` "testPassword"
    }


    @Test
    fun `test delete Customer`() {
        // prepare database
        val storedCustomer = newCustomer()
        val entity = repository.save(storedCustomer)

        //execute function under test
        val request = delete("${CustomerController.URI_CUSTOMER_BASE}/${entity.id}")
        mockMvc
                .perform(request)
                .andExpect(status().isOk) // validate response

        // validate entity has been deleted
        val result = repository.findById(entity.id)
        result.isEmpty `should be equal to` true
    }

    private fun newCustomer(): Customer {
        return Customer("Jan","testStrasse 65", "jan@juergenberger.de","testPassword")
    }
}