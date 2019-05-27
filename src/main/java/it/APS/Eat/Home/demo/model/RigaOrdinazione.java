package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@Document
public class RigaOrdinazione {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codiceRigaOrdinazione;

    @Field
    private String nota;

    @Field
    private Integer quantita;

    @Field
    private Float totaleRiga;

    @Field
    private String codiceProdotto;

}
