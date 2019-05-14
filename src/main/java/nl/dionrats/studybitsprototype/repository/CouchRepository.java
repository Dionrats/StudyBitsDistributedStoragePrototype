package nl.dionrats.studybitsprototype.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.adapters.DocumentAdapter;
import nl.dionrats.studybitsprototype.entity.Document;
import nl.dionrats.studybitsprototype.entity.CouchDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component("couchdb")
public class CouchRepository implements IRepository {

    private String connectionstring;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Autowired
    public CouchRepository(Environment env) {
        log.debug("======== Using Couch Repository ======");
        String host = env.getProperty("PROVIDER_HOST");
        connectionstring = host + "/transcripts/";
        log.debug("HOST: {}", connectionstring);
        initObjectMapper();
        initDatabase();

        //create redundantie?
    }

    @Override
    public Document getFile(String key) {
        try {
            return DocumentAdapter.toDocument(Unirest.get(connectionstring + key).asObject(CouchDocument.class).getBody());
        } catch (UnirestException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void storeFile(String key, Document document) {
        try {
            Unirest.put(connectionstring + key).body(document).asJson();
        } catch (UnirestException e) {
            log.error(e.getMessage());
        }
    }

    private void initDatabase() {
        try {
            log.debug("DB: {}", Unirest.put(connectionstring)
                    .basicAuth(USERNAME, PASSWORD)
                    .asJson()
                    .getBody());
        } catch (UnirestException e) {
            log.error(e.getMessage());
        }
    }

    private void initObjectMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
