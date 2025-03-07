package my.vb.sportbook.dataretriever.service.impl;

import lombok.extern.slf4j.Slf4j;
import my.vb.sportbook.dataretriever.dto.EventDTO;
import my.vb.sportbook.dataretriever.model.Event;
import my.vb.sportbook.dataretriever.repository.EventRepository;
import my.vb.sportbook.dataretriever.service.CRUDProcessor;
import my.vb.sportbook.dataretriever.service.CacheService;
import my.vb.sportbook.dataretriever.service.EventService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventServiceImpl extends CRUDProcessor<Event, MongoRepository<Event, Long>, EventDTO> implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(CacheService cacheService, EventRepository eventRepository) {
        super(cacheService);
        this.eventRepository = eventRepository;
    }

    @Override
    public EventRepository getRepository() {
        return eventRepository;
    }

    @Override
    public Event convertTo(EventDTO dto) {
        log.info("EventDTO convert");
        return dto.convertTo();
    }

    @Override
    public EventDTO convertFrom(Event event) {
        return new EventDTO(event);
    }
}
