package nl.dionrats.studybitsprototype.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {
    private String name;
    private String type;
    private byte[] data;
}
