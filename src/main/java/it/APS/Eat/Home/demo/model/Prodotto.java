package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@Document
public class Prodotto {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codice;

    @Field
    private String nome;

    @Field
    private String descrizione;

    @Field
    private Integer prezzo;

    @Field
    private Boolean specialita;

    @Field
    private Menu menu;

}
