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
public class Menu {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codiceMenu;

    @Field
    private String specialita;

    @Field
    private List<String> codiciProdotti;

    @Field
    private Boolean isListaProdottiCompleta;

}