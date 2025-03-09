package my.vb.sportbook.dataretriever.healthcheck;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    public static final String MYDATABASE = "sportbook";
    private final MongoClient mongoClient;


    @Override
    public Health health() {

        boolean dbIsHealthy = checkDatabaseConnection();
        if (dbIsHealthy) {
            return Health.up().withDetail("database", "Database is reachable").build();
        } else {
            return Health.down().withDetail("database", "Database is unreachable").build();
        }
    }

    private boolean checkDatabaseConnection() {
        try {
            mongoClient.getDatabase(MYDATABASE).runCommand(new org.bson.Document("ping", 1));
            return true;
        } catch (MongoClientException exception) {
            System.err.println("MongoDB connection failed: " + exception.getMessage());
            return false;
        }

    }
}

