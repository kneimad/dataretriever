package my.vb.sportbook.dataretriever.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@TypeAlias("Event_history")
@Document(collection = "event_history")
public class EventHistory {
    @Id
    private String id;
    private Long eventId;

    private String description;
    private String homeTeam;
    private String awayTeam;
    private Instant startTime;
    private String sport;
    private String country;
    private String competition;
    private Boolean settled;

    private Instant updatedDateTime;
    private String updatedUser;

    private String changeType;
    private Instant changeTimestamp;
}

