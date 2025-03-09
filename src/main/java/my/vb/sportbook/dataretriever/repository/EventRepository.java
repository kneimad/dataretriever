package my.vb.sportbook.dataretriever.repository;

import my.vb.sportbook.dataretriever.model.Event;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, Long> {
    @Query("{ 'settled': false, 'sport': ?#{ [0] == null ? {$exists: true} : [0] } }")
    List<Event> findBySettledIsFalseAndSport(String sport, Sort sort);
}

