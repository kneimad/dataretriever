package my.vb.sportbook.dataretriever.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.vb.sportbook.dataretriever.model.Event;
import my.vb.sportbook.dataretriever.model.EventHistory;
import my.vb.sportbook.dataretriever.repository.EventHistoryRepository;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventAuditListener extends AbstractMongoEventListener<Event> {

    private final EventHistoryRepository eventHistoryRepository;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Event> event) {
        Event sourceEvent = event.getSource();

        EventHistory eventHistory = EventHistory.builder()
                .eventId(sourceEvent.getId())
                .description(sourceEvent.getDescription())
                .homeTeam(sourceEvent.getHomeTeam())
                .awayTeam(sourceEvent.getAwayTeam())
                .startTime(sourceEvent.getStartTime())
                .sport(sourceEvent.getSport())
                .country(sourceEvent.getCountry())
                .competition(sourceEvent.getCompetition())
                .settled(sourceEvent.isSettled())
                .updatedDateTime(sourceEvent.getUpdatedDateTime())
                .updatedUser(sourceEvent.getUpdatedUser())
                .changeType("UPDATE")
                .changeTimestamp(Instant.now())
                .build();

        eventHistoryRepository.save(eventHistory);

        log.info("Audit for Event ID {} has been created", sourceEvent.getId());
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Event> event) {

        Long eventId = (Long) event.getSource().get("_id");

        EventHistory eventHistory = EventHistory.builder()
                .eventId(eventId)
                .changeType("DELETE")
                .changeTimestamp(Instant.now())
                .build();

        eventHistoryRepository.save(eventHistory);

        log.info("Audit for Event ID {} has been deleted", eventId);
    }
}
