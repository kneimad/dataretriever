package my.vb.sportbook.dataretriever.controller;

import my.vb.sportbook.dataretriever.dto.EventDTO;
import my.vb.sportbook.dataretriever.dto.MarketDTO;
import my.vb.sportbook.dataretriever.dto.OutcomeDTO;
import my.vb.sportbook.dataretriever.model.StatusEnum;
import my.vb.sportbook.dataretriever.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should return 200 OK with detailed EventDTO when the entity is found")
    void findById_detailedEntityFound_returnsEventDTO() throws Exception {
        Long existingId = 7L;

        OutcomeDTO outcome1 = new OutcomeDTO(1001L, "Team A Wins", false, new BigDecimal("1.5"), "win");
        OutcomeDTO outcome2 = new OutcomeDTO(1002L, "Team B Wins", false, new BigDecimal("2.0"), "win");
        OutcomeDTO outcome3 = new OutcomeDTO(1003L, "Draw", false, new BigDecimal("3.5"), "lose");

        OutcomeDTO outcome4 = new OutcomeDTO(2001L, "Over 2.5 Goals", false, new BigDecimal("1.8"), "win");
        OutcomeDTO outcome5 = new OutcomeDTO(2002L, "Under 2.5 Goals", false, new BigDecimal("2.1"), "lose");

        MarketDTO market1 = new MarketDTO(101L, "Who will win?", StatusEnum.OPEN, false, List.of(outcome1, outcome2, outcome3));
        MarketDTO market2 = new MarketDTO(102L, "Total Goals", StatusEnum.OPEN, false, List.of(outcome4, outcome5));

        EventDTO mockEvent = new EventDTO();
        mockEvent.setId(existingId);
        mockEvent.setDescription("Champions League Final 2025");
        mockEvent.setHomeTeam("Team A");
        mockEvent.setAwayTeam("Team B");
        mockEvent.setStartTime(Instant.parse("2025-11-30T18:00:00Z"));
        mockEvent.setSport("Football");
        mockEvent.setCountry("England");
        mockEvent.setCompetition("Champions League");
        mockEvent.setSettled(false);
        mockEvent.setMarkets(List.of(market1, market2));

        when(eventService.findById(existingId)).thenReturn(mockEvent);

        mockMvc.perform(get("/event/find/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                            {
                              "id": 7,
                              "description": "Champions League Final 2025",
                              "homeTeam": "Team A",
                              "awayTeam": "Team B",
                              "startTime": "2025-11-30T18:00:00Z",
                              "sport": "Football",
                              "country": "England",
                              "competition": "Champions League",
                              "settled": false,
                              "markets": [
                                {
                                  "id": 101,
                                  "description": "Who will win?",
                                  "status": "OPEN",
                                  "settled": false,
                                  "outcomes": [
                                    {
                                      "id": 1001,
                                      "description": "Team A Wins",
                                      "settled": false,
                                      "price": 1.5,
                                      "result": "win"
                                    },
                                    {
                                      "id": 1002,
                                      "description": "Team B Wins",
                                      "settled": false,
                                      "price": 2.0,
                                      "result": "win"
                                    },
                                    {
                                      "id": 1003,
                                      "description": "Draw",
                                      "settled": false,
                                      "price": 3.5,
                                      "result": "lose"
                                    }
                                  ]
                                },
                                {
                                  "id": 102,
                                  "description": "Total Goals",
                                  "status": "OPEN",
                                  "settled": false,
                                  "outcomes": [
                                    {
                                      "id": 2001,
                                      "description": "Over 2.5 Goals",
                                      "settled": false,
                                      "price": 1.8,
                                      "result": "win"
                                    },
                                    {
                                      "id": 2002,
                                      "description": "Under 2.5 Goals",
                                      "settled": false,
                                      "price": 2.1,
                                      "result": "lose"
                                    }
                                  ]
                                }
                              ]
                            }
                        """));
    } {
        Long existingId = 7L;

        OutcomeDTO outcome1 = new OutcomeDTO(1001L, "Team A Wins", false, new BigDecimal("1.5"), "win");
        OutcomeDTO outcome2 = new OutcomeDTO(1002L, "Team B Wins", false, new BigDecimal("2.0"), "win");
        OutcomeDTO outcome3 = new OutcomeDTO(1003L, "Draw", false, new BigDecimal("3.5"), "lose");

        OutcomeDTO outcome4 = new OutcomeDTO(2001L, "Over 2.5 Goals", false, new BigDecimal("1.8"), "win");
        OutcomeDTO outcome5 = new OutcomeDTO(2002L, "Under 2.5 Goals", false, new BigDecimal("2.1"), "lose");

        MarketDTO market1 = new MarketDTO(101L, "Who will win?", StatusEnum.OPEN, false, List.of(outcome1, outcome2, outcome3));
        MarketDTO market2 = new MarketDTO(102L, "Total Goals", StatusEnum.OPEN, false, List.of(outcome4, outcome5));

        EventDTO mockEvent = new EventDTO();
        mockEvent.setId(existingId);
        mockEvent.setDescription("Champions League Final 2025");
        mockEvent.setHomeTeam("Team A");
        mockEvent.setAwayTeam("Team B");
        mockEvent.setStartTime(Instant.parse("2025-11-30T18:00:00Z"));
        mockEvent.setSport("Football");
        mockEvent.setCountry("England");
        mockEvent.setCompetition("Champions League");
        mockEvent.setSettled(false);
        mockEvent.setMarkets(List.of(market1, market2));

        when(eventService.findById(existingId)).thenReturn(mockEvent);

        try {
            mockMvc.perform(get("/event/find/{id}", existingId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                                {
                                  "id": 7,
                                  "description": "Champions League Final 2025",
                                  "homeTeam": "Team A",
                                  "awayTeam": "Team B",
                                  "startTime": "2025-11-30T18:00:00Z",
                                  "sport": "Football",
                                  "country": "England",
                                  "competition": "Champions League",
                                  "settled": false,
                                  "markets": [
                                    {
                                      "id": 101,
                                      "description": "Who will win?",
                                      "status": "OPEN",
                                      "settled": false,
                                      "outcomes": [
                                        {
                                          "id": 1001,
                                          "description": "Team A Wins",
                                          "settled": false,
                                          "price": 1.5,
                                          "result": "win"
                                        },
                                        {
                                          "id": 1002,
                                          "description": "Team B Wins",
                                          "settled": false,
                                          "price": 2.0,
                                          "result": "win"
                                        },
                                        {
                                          "id": 1003,
                                          "description": "Draw",
                                          "settled": false,
                                          "price": 3.5,
                                          "result": "lose"
                                        }
                                      ]
                                    },
                                    {
                                      "id": 102,
                                      "description": "Total Goals",
                                      "status": "OPEN",
                                      "settled": false,
                                      "outcomes": [
                                        {
                                          "id": 2001,
                                          "description": "Over 2.5 Goals",
                                          "settled": false,
                                          "price": 1.8,
                                          "result": "win"
                                        },
                                        {
                                          "id": 2002,
                                          "description": "Under 2.5 Goals",
                                          "settled": false,
                                          "price": 2.1,
                                          "result": "lose"
                                        }
                                      ]
                                    }
                                  ]
                                }
                            """));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Should return 404 NOT FOUND when the detailed entity is not found")
    void findById_detailedEntityNotFound_returns404() throws Exception {
        Long nonExistentId = 999L;

        when(eventService.findById(nonExistentId)).thenReturn(null);

        mockMvc.perform(get("/event/find/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 OK with the list of non-settled EventDTOs when valid parameters are provided")
    void findAllNonSettledWithParams_validParams_returnsNonSettledEvents() throws Exception {
        String sportFilter = "Football";
        Boolean sortedByStartTime = true;

        EventDTO event1 = new EventDTO();
        event1.setId(1L);
        event1.setDescription("Match 1");
        event1.setSport("Football");
        event1.setStartTime(Instant.parse("2025-12-01T15:00:00Z"));

        EventDTO event2 = new EventDTO();
        event2.setId(2L);
        event2.setDescription("Match 2");
        event2.setSport("Football");
        event2.setStartTime(Instant.parse("2025-12-02T15:00:00Z"));

        List<EventDTO> mockEvents = List.of(event1, event2);

        when(eventService.nonSettledEvents(sportFilter, sortedByStartTime)).thenReturn(mockEvents);

        mockMvc.perform(get("/event/findAllNonSettled")
                        .param("sport", sportFilter)
                        .param("sortedByStartTime", sortedByStartTime.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                            {
                                "id": 1,
                                "description": "Match 1",
                                "sport": "Football",
                                "startTime": "2025-12-01T15:00:00Z"
                            },
                            {
                                "id": 2,
                                "description": "Match 2",
                                "sport": "Football",
                                "startTime": "2025-12-02T15:00:00Z"
                            }
                        ]
                        """));
    }

    @Test
    @DisplayName("Should return 200 OK with an empty list when no non-settled events are found")
    void findAllNonSettledWithParams_noResults_returnsEmptyList() throws Exception {
        String sportFilter = "Cricket";
        Boolean sortedByStartTime = false;

        when(eventService.nonSettledEvents(sportFilter, sortedByStartTime)).thenReturn(List.of());

        mockMvc.perform(get("/event/findAllNonSettled")
                        .param("sport", sportFilter)
                        .param("sortedByStartTime", sortedByStartTime.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST when invalid parameters are provided")
    void findAllNonSettledWithParams_invalidParams_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/event/findAllNonSettled")
                        .param("sortedByStartTime", "not-a-boolean")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}