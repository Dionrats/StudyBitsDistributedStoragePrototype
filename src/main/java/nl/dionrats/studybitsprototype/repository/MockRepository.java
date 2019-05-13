package nl.dionrats.studybitsprototype.repository;

import nl.dionrats.studybitsprototype.entity.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.HashMap;

@Slf4j
@Repository("Mock")
public class MockRepository implements IRepository {

    private HashMap<String, Document> documents;

    public MockRepository() {
        log.debug("======== Using Mock Repository ======");
        this.documents = new HashMap<>();
    }

    public Document getFile(String key) {
        log.debug("getting file with key {}", key);
        return documents.get(key);
    }

    public void storeFile(String key, Document document) {
        log.debug("Storing file {} with key {}", document.getName(), key);
        documents.put(key, document);
    }

}
