package my.vb.sportbook.dataretriever.controller;

import my.vb.sportbook.dataretriever.model.IndexedEntity;
import my.vb.sportbook.dataretriever.service.CRUDMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public interface CRUDControllerInterface<D, T extends IndexedEntity, E extends CRUDMethods<D>> {

    Logger logger = LoggerFactory.getLogger(CRUDControllerInterface.class);

    @PostMapping("/create")
    default ResponseEntity<D> create(@RequestBody D dto) {
        D createdEntity = getService().create(dto);
        return new ResponseEntity<>(createdEntity, OK);
    }

    @PutMapping("/update")
    default ResponseEntity<D> update(@RequestBody D dto) {
        D updatedEntity = getService().update(dto);
        return new ResponseEntity<>(updatedEntity, OK);
    }

    @DeleteMapping("/delete/{id}")
    default void deleteById(@PathVariable(name = "id") Long id) {
        getService().deleteById(id);
    }

    @GetMapping("/find/{id}")
    default ResponseEntity<D> findById(@PathVariable(name = "id") Long id) {
        logger.info("Fetching entity with ID: {}", id);
        D entity = getService().findById(id);
        if (entity == null) {
            logger.warn("Entity with ID {} not found", id);
            return new ResponseEntity<>(null, NOT_FOUND);
        }
        logger.info("Entity with ID {} found: {}", id, entity);
        return new ResponseEntity<>(entity, OK);
    }

    CRUDMethods<D> getService();
}
