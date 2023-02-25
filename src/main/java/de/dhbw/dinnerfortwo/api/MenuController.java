package de.dhbw.dinnerfortwo.api;

import de.dhbw.dinnerfortwo.impl.Menu.Menu;
import de.dhbw.dinnerfortwo.impl.Menu.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static de.dhbw.dinnerfortwo.api.MenuController.URI_MENU_BASE;
import static de.dhbw.dinnerfortwo.api.MetaInfo.URI_BASE;

/**
 * REST (HTTP) API of the Dinner app to interact with the UI or external applications.
 * The REST API provides the CRUD operations to create, read, update or delete a meal.
 */
@RestController
@RequestMapping(value = URI_MENU_BASE, produces = "application/json;charset=UTF-8")
public class MenuController {

    public static final String URI_MENU_BASE = URI_BASE + "/menus";

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable UUID id) {
        log.info("Get menu with id {}", id);
        try {
            var menu = menuService.getMenuById(id);
            return ResponseEntity.ok(menu);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        log.info("Get all menus");
        var result = menuService.getAllMenus();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody MenuDTO newMenu) {
        Menu menu = new Menu(newMenu.getCategory()); // enforce a new ID
        Menu result = menuService.createMenu(menu);
        log.info("Created menu {}", result);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Update existing meal, with a given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateMenu(@PathVariable UUID id, @RequestBody MenuDTO menu) {
        Menu updateMenu = new Menu(id.toString(), menu.getCategory()); // enforce the id of the parameter ID
        menuService.updateMenu(updateMenu);
        log.info("updated meal {}", updateMenu);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMeal(@PathVariable UUID id) {
        menuService.deleteMenuById(id);
    }

    public static class MenuDTO {

        @Column(nullable = false)
        private String category;

        public MenuDTO() {
        }

        public MenuDTO(String name, double price, String category) {
            this.category = category;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "MealDTO{" +
                    ", category='" + category + '\'' +
                    '}';
        }
    }
}
