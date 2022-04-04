package ulaval.glo2003.infrastructure.persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import org.bson.UuidRepresentation;
import ulaval.glo2003.Environment;

import static java.util.concurrent.TimeUnit.SECONDS;

public class DbConnection {
    private final String mongoUrl;
    private final String db;
    private final String PROD_URL = "mongodb+srv://floppa-api:v2E1vy9ACkzmB2N5@floppacluster.uk21j.mongodb.net/?retryWrites=true&w=majority";
    private final String STAGING_URL = "mongodb+srv://floppa-api:v2E1vy9ACkzmB2N5@floppacluster.uk21j.mongodb.net/?retryWrites=true&w=majority";
    private final String TEST_URL = "mongodb://localhost";


    public DbConnection(Environment environment) {
        if (environment == Environment.PRODUCTION) {
            mongoUrl = PROD_URL;
            db = "floppa-production";
        } else if (environment == Environment.STAGING) {
            mongoUrl = STAGING_URL;
            db = "floppa-staging";
        } else {
            mongoUrl = TEST_URL;
            db = "floppa-dev";
        }
    }

    public boolean canConnectToProduction() {
        try (MongoClient mongoClient = MongoClients.create(getMongoSettings(PROD_URL))) {
            try {
                var database = mongoClient.getDatabase("floppa-production");
                var collection = database.listCollectionNames().first();
                return collection != null;

            } catch (MongoException me) {
                return false;
            }
        }
    }

    public Datastore getDataStore() {
        MapperOptions morphiaSettings = MapperOptions.builder().uuidRepresentation(UuidRepresentation.STANDARD).build();
        Datastore datastore = Morphia.createDatastore(MongoClients.create(getMongoSettings(mongoUrl)), db, morphiaSettings);
        datastore.getMapper().mapPackage("ulaval.glo2003.infrastructure.persistence.model");

        return datastore;
    }

    public void dropTestDatabase(){
        MongoClient mongoClient = MongoClients.create(getMongoSettings(mongoUrl));
        var database = mongoClient.getDatabase("floppa-dev");
        database.drop();
    }

    private MongoClientSettings getMongoSettings(String url) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .applyToConnectionPoolSettings(builder ->
                        builder.maxConnectionIdleTime(5, SECONDS))
                .applyToClusterSettings(builder ->
                        builder.serverSelectionTimeout(5, SECONDS))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }
}
