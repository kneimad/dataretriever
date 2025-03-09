package my.vb.sportbook.dataretriever.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.vb.sportbook.dataretriever.dto.EventDTO;
import my.vb.sportbook.dataretriever.model.Event;
import my.vb.sportbook.dataretriever.service.EventService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * REST controller for handling requests related to events.
 * Provides endpoints for retrieving event details and non-settled events.
 */
@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/find/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable(name = "id") Long id) {
        log.info("Fetching entity with ID: {}", id);
        EventDTO entity = eventService.findById(id);
        if (entity == null) {
            log.warn("Entity with ID {} not found", id);
            return new ResponseEntity<>(null, NOT_FOUND);
        }
        log.info("Entity with ID {} found: {}", id, entity);
        return new ResponseEntity<>(entity, OK);
    }

    @GetMapping("/findAllNonSettled")
    public ResponseEntity<List<EventDTO>> findAllNonSettledWithParams(
                                                  @RequestParam(required = false, name = "sport") String sport,
                                                  @RequestParam(required = false, defaultValue = "false", name = "sortedByStartTime") Boolean sortedByStartTime)
    {
        log.info("Fetching all non settles Events with excludeMarkets filtered by sport: {} sorted by StartTime {}", sport, sortedByStartTime);
        List<EventDTO> eventDTOS = eventService.nonSettledEvents(sport, sortedByStartTime);
        if (eventDTOS == null) {
            log.warn("Non settles Events not found");
            return new ResponseEntity<>(null, NOT_FOUND);
        }
        log.info("Non settles Events found: {}", eventDTOS);
        return new ResponseEntity<>(eventDTOS, OK);
    }

}
