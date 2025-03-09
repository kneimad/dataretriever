package my.vb.sportbook.dataretriever.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Event Controller", description = "Endpoints for managing and retrieving event data")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Find Event by ID",
            description = "Fetches an event using its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Event not found")
            }
    )
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
    @Operation(
            summary = "Find All Non-Settled Events",
            description = "Fetches a list of all non-settled events optionally filtered by sport and sorted by start time",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Non-settled events retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "No non-settled events found")
            }
    )
    public ResponseEntity<List<EventDTO>> findAllNonSettledWithParams(
            @RequestParam(required = false, name = "sport") String sport,
            @RequestParam(required = false, defaultValue = "false", name = "sortedByStartTime") Boolean sortedByStartTime) {
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
