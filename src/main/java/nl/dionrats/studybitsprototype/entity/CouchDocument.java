package nl.dionrats.studybitsprototype.entity;

import lombok.Data;

@Data
public class CouchDocument {

    private String _id;
    private String _rev;
    private String name;
    private String type;
    private byte[] data;
}
