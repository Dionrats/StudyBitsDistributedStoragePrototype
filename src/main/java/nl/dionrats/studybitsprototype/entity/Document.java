package nl.dionrats.studybitsprototype.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {
    private String name;
    private String type;
    private byte[] data;


}
