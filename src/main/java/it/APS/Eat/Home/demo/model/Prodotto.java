package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import java.math.BigDecimal;

@Data
@Document
public class Prodotto {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codiceProdotto;

    @Field
    private String nome;

    @Field
    private String descrizione;

    @Field
    private String prezzo;

    @Field
    private Boolean specialita;

}
