package nl.dionrats.studybitsprototype.repository;

import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;


@Slf4j
@Repository("Voldemort")
public class VoldemortRepository implements IRepository{

    private static final String STORE_NAME = "documents";
    private StoreClient<String, Document> client;

    @Autowired
    public VoldemortRepository(Environment env) {
        log.debug("====== Using Voldemort Repository ======");
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls("tcp://" + env.getProperty("voldemort.host") + ":" + env.getProperty("voldemort.port")));
        client = factory.getStoreClient(STORE_NAME);

    }

    @Override
    public Document getFile(String key) {
        return client.get(key).getValue();
    }

    @Override
    public String storeFile(String key, Document document) {
        client.put(key, document);
        return key;
    }

}
