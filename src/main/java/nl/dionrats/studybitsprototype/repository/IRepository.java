package nl.dionrats.studybitsprototype.repository;

import nl.dionrats.studybitsprototype.entity.Document;

public interface IRepository {
    Document getFile(String key);
    void storeFile(String key, Document document);
}
