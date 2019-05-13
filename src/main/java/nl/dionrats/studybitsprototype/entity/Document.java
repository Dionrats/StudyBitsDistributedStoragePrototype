package nl.dionrats.studybitsprototype.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String name;
    private String type;
    private byte[] data;


}
