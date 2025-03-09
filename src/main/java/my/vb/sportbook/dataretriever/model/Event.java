package my.vb.sportbook.dataretriever.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@TypeAlias("Event")
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "events")
public class Event implements IndexedEntity {
    @Id
    private Long id;
    private String description;
    private String homeTeam;
    private String awayTeam;
    private Instant startTime;
    private String sport;
    private String country;
    private String competition;
    private boolean settled;
    @LastModifiedDate
    protected Instant updatedDateTime;
    @LastModifiedBy
    protected String updatedUser;

    private List<Market> markets;
}
