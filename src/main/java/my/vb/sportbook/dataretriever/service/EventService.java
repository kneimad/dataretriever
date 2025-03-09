package my.vb.sportbook.dataretriever.service;

import my.vb.sportbook.dataretriever.dto.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO findById(Long id);
    List<EventDTO> nonSettledEvents(String sport, Boolean sortedByStartTime);
}
