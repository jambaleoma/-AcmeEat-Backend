package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.Set;

@Data
@Document
public class Utilities {

    @Field
    private String codiceUtilities;

    @Field
    private Set<Citta> listacitta;
}
