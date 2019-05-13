package nl.dionrats.studybitsprototype.adapters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import nl.dionrats.studybitsprototype.entity.Document;
import nl.dionrats.studybitsprototype.entity.CouchDocument;
import nl.dionrats.studybitsprototype.entity.DocumentDTO;

import java.io.IOException;

public class DocumentAdapter {

    public static DBObject toMongoDBObject(String key, Document document) {
        return new BasicDBObject("_id", key)
                .append("name", document.getName())
                .append("type", document.getType())
                .append("data", document.getData());
    }

    public static Document toDocument(DocumentDTO dto) throws IOException {
        return new Document(dto.getName(), dto.getType(), dto.getFile().getBytes());
    }

    public static Document toDocument(DBObject dbObject) {
        return new Document(
                (String)dbObject.get("name"),
                (String)dbObject.get("type"),
                (byte[])dbObject.get("data"));
    }

    public static Document toDocument(CouchDocument documentCouch) {
        return new Document(
                documentCouch.getName(),
                documentCouch.getType(),
                documentCouch.getData());
    }
}
