package my.vb.sportbook.dataretriever.service.impl;

import lombok.extern.slf4j.Slf4j;
import my.vb.sportbook.dataretriever.dto.EventDTO;
import my.vb.sportbook.dataretriever.exception.EntityNotFoundException;
import my.vb.sportbook.dataretriever.model.Event;
import my.vb.sportbook.dataretriever.repository.EventRepository;
import my.vb.sportbook.dataretriever.service.EventService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Cacheable(value = "entitiesCache", key = "#id")
    public EventDTO findById(Long id) {
        return convertFrom(eventRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entity with id " + id + " not found")), false);
    }

    @Override
    @Cacheable(value = "collectionEntitiesCache", key = "#sport + '-' + #sortedByStartTime")
    public List<EventDTO> nonSettledEvents(String sport, Boolean sortedByStartTime) {
        Sort sort = Optional.ofNullable(sortedByStartTime)
                .filter(Boolean::booleanValue)
                .map(val -> Sort.by(Sort.Order.desc("startTime"))).orElse(null);

        return eventRepository.findBySettledIsFalseAndSport(sport, sort)
                .stream().map(event -> convertFrom(event, true)).toList();
    }

    private EventDTO convertFrom(Event event, Boolean excludeMarkets) {
        log.info("EventDTO to Event convert");
        return new EventDTO(event, excludeMarkets);
    }
}
