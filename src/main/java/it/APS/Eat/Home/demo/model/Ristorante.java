package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

@Data
@Document
public class Ristorante {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codice;

    @Field
    private String nome;

    @Field
    private String descrizione;

    @Field
    private Integer posizione;

    @Field
    private Menu menu;

    @Field
    private Cucina cucina;

    @Field
    private Direttore direttore;

    @Field
    private Citta citta;

    @Field
    private List<Ordinazione> oridinazioni;

}
