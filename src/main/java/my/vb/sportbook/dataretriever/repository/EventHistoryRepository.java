package my.vb.sportbook.dataretriever.repository;

import my.vb.sportbook.dataretriever.model.EventHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventHistoryRepository extends MongoRepository<EventHistory, Long> {

}

