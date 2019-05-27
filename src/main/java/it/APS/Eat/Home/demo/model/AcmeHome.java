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
public class AcmeHome {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codiceAzienda;

    @Field
    private List<Citta> citta;

    @Field
    private List<Direttore> direttori;

    @Field
    private List<Consumatore> consumatori;

    @Field
    private List<Ristorante> ristoranti;

    @Field
    private List<String> codiciQuindiciRistorantiRecenti;
}
