package de.dhbw.dinnerfortwo.impl.Menu;

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
public class MenuService {
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    private final MenuRepository menuRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public Menu getMenuById(UUID id) {
        log.info("Looking for an menu with id {}", id);
        return menuRepository.findById(id.toString()).orElseThrow(() -> new EntityNotFoundException("Could not find menu with Id " + id));
    }

    @Transactional
    public List<Menu> getAllMenus() {
        log.info("Get all menus");
        return menuRepository.findAll().stream().toList();
    }

    @Transactional
    public Menu createMenu(Menu menu) {
        log.info("Save or update menu {}", menu);
        return menuRepository.save(menu);
    }

    @Transactional
    public void updateMenu(Menu menu) {
        var persisted = menuRepository.findById(menu.getId()).orElseThrow(() -> new EntityNotFoundException("Could not find menu with Id " + menu.getId()));
        persisted.setCategory(menu.getCategory());
        menuRepository.save(menu);
    }

    public void deleteMenuById(UUID id) {
        log.info("Deleting menu with id {}", id);
        menuRepository.deleteById(id.toString());
    }
}
