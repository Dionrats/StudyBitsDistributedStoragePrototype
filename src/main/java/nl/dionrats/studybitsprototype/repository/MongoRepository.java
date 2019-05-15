package nl.dionrats.studybitsprototype.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.adapters.DocumentAdapter;
import nl.dionrats.studybitsprototype.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository("mongodb")
public class MongoRepository implements IRepository {

    private DBCollection collection;

    @Autowired
    public MongoRepository(MongoClient mongoClient) {
        log.debug("======== Using Mongo Repository ======");
        //TODO move dbname and collectionname to properties
        this.collection = mongoClient.getDB("StudyBits").getCollection("Documents");
    }

    @Override
    public Document getFile(String key) {
        DBObject query = new BasicDBObject("_id", key);
        return DocumentAdapter.toDocument(collection.find(query).one());
    }

    @Override
    public String storeFile(String key, Document document) {
        collection.insert(DocumentAdapter.toMongoDBObject(key, document));

        return key;
    }


}
