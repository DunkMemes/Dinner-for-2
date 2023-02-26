package de.dhbw.dinnerfortwo

import com.fasterxml.jackson.databind.ObjectMapper
import de.dhbw.dinnerfortwo.api.MenuController
import de.dhbw.dinnerfortwo.impl.Menu.Menu
import de.dhbw.dinnerfortwo.impl.Menu.MenuRepository
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
class MenuControllerTest(@Autowired val repository: MenuRepository) {

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
    fun `test rest end point to create a new Menu`() {
        // prepare test
        val menu = newMenu()
        val request = post(MenuController.URI_MENU_BASE)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(menu))

        // execute function under test
        val result = mockMvc
                .perform(request)
                .andExpect(status().isCreated)
                .andReturn()

        // evaluate result
        val createdMenu = objectMapper.readValue(result.response.contentAsString, menu::class.java)
        createdMenu.category `should be equal to` "Meat"
    }

    @Test
    fun `test endpoint to read a Menu by id`() {
        // prepare database
        val storedMenu = newMenu()
        val entity = repository.save(storedMenu)

        // execute function under test
        val request = get("${MenuController.URI_MENU_BASE}/${entity.id}")
                .contentType("application/json")
        val result = mockMvc.perform(request)
                .andExpect(status().isOk)
                .andReturn()

        // evaluate result
        val resultMenu = objectMapper.readValue(result.response.contentAsString, Menu::class.java)
        resultMenu.category `should be equal to` "Meat"
    }

    @Test
    fun `test update Menu`() {
        // prepare database
        val storedMenu = newMenu()
        val entity = repository.save(storedMenu)

        val update = MenuController.MenuDTO("Veggie")
        val request = put("${MenuController.URI_MENU_BASE}/${entity.id}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(update))
        mockMvc
                .perform(request)
                //            .andDo { print(it) } // just for debugging
                .andExpect(status().isOk) // validate return value
                .andReturn()

        // validate result
        val updatedMenu = repository.findById(entity.id).get()
        updatedMenu.category `should be equal to` "Veggie"
    }


    @Test
    fun `test delete Menu`() {
        // prepare database
        val storedMenu = newMenu()
        val entity = repository.save(storedMenu)

        //execute function under test
        val request = delete("${MenuController.URI_MENU_BASE}/${entity.id}")
        mockMvc
                .perform(request)
                .andExpect(status().isOk) // validate response

        // validate entity has been deleted
        val result = repository.findById(entity.id)
        result.isEmpty `should be equal to` true
    }

    private fun newMenu(): Menu {
        return Menu("Meat")
    }
}