package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Set;

@Data
@Document
public class Direttore {

    @Id
    private String codiceDirettore;

    @Field
    private String psw;

    @Field
    private String nome;

    @Field
    private String cognome;

    @Field
    private Set<String> ristoranti;

}
