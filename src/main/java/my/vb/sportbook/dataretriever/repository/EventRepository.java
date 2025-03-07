package my.vb.sportbook.dataretriever.repository;

import my.vb.sportbook.dataretriever.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {

}

