package my.vb.sportbook.dataretriever.service.impl;

import my.vb.sportbook.dataretriever.dto.EventDTO;
import my.vb.sportbook.dataretriever.exception.EntityNotFoundException;
import my.vb.sportbook.dataretriever.model.Event;
import my.vb.sportbook.dataretriever.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_exists_success() {
        Event mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setDescription("Event Description");
        mockEvent.setHomeTeam("Home Team");
        mockEvent.setAwayTeam("Away Team");
        mockEvent.setSport("Sport");
        mockEvent.setSettled(false);
        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        EventDTO result = eventService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Event Description", result.getDescription());
        assertEquals("Home Team", result.getHomeTeam());
        assertEquals("Away Team", result.getAwayTeam());
        assertEquals("Sport", result.getSport());
        Mockito.verify(eventRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void findById_notFound_throwsException() {
        Mockito.when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.findById(1L));

        Mockito.verify(eventRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void findById_cachesResult_success() {
        Event mockEvent = new Event();
        mockEvent.setId(1L);
        mockEvent.setDescription("Event Description");
        mockEvent.setHomeTeam("Home Team");
        mockEvent.setAwayTeam("Away Team");
        mockEvent.setSport("Sport");
        mockEvent.setSettled(false);

        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(mockEvent));

        // Call the method twice
        eventService.findById(1L);
        eventService.findById(1L);

        // Verify the repository is called only once due to caching
        Mockito.verify(eventRepository, times(1)).findById(1L);
    }

    @Test
    void nonSettledEvents_withSorting_success() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setSport("Football");
        event1.setSettled(false);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setSport("Football");
        event2.setSettled(false);

        Mockito.when(eventRepository.findBySettledIsFalseAndSport("Football", Sort.by(Sort.Order.desc("startTime"))))
                .thenReturn(List.of(event2, event1));

        List<EventDTO> results = eventService.nonSettledEvents("Football", true);

        assertEquals(2, results.size());
        assertEquals(2L, results.get(0).getId());
        assertEquals(1L, results.get(1).getId());

        Mockito.verify(eventRepository, times(1))
                .findBySettledIsFalseAndSport("Football", Sort.by(Sort.Order.desc("startTime")));
    }

    @Test
    void nonSettledEvents_withoutSorting_success() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setSport("Basketball");
        event1.setSettled(false);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setSport("Basketball");
        event2.setSettled(false);

        Mockito.when(eventRepository.findBySettledIsFalseAndSport("Basketball", null))
                .thenReturn(List.of(event1, event2));

        List<EventDTO> results = eventService.nonSettledEvents("Basketball", false);

        assertEquals(2, results.size());
        assertEquals(1L, results.get(0).getId());
        assertEquals(2L, results.get(1).getId());

        Mockito.verify(eventRepository, times(1))
                .findBySettledIsFalseAndSport("Basketball", null);
    }

    @Test
    void nonSettledEvents_noResults_emptyList() {
        Mockito.when(eventRepository.findBySettledIsFalseAndSport("Tennis", null))
                .thenReturn(List.of());

        List<EventDTO> results = eventService.nonSettledEvents("Tennis", false);

        assertEquals(0, results.size());

        Mockito.verify(eventRepository, times(1))
                .findBySettledIsFalseAndSport("Tennis", null);
    }
}