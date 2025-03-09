package my.vb.sportbook.dataretriever.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.vb.sportbook.dataretriever.model.Event;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private String homeTeam;
    private String awayTeam;
    private Instant startTime;
    private String sport;
    private String country;
    private String competition;
    private boolean settled;

    private List<MarketDTO> markets;

    public EventDTO(Event event, Boolean excludeMarkets) {
        this.id = event.getId();
        this.description = event.getDescription();
        this.homeTeam = event.getHomeTeam();
        this.awayTeam = event.getAwayTeam();
        this.startTime = event.getStartTime();
        this.sport = event.getSport();
        this.country = event.getCountry();
        this.competition = event.getCompetition();
        this.settled = event.isSettled();
        this.markets = (excludeMarkets==null || !excludeMarkets) ? event.getMarkets().stream().map(MarketDTO::new).toList() : null;
    }

}
