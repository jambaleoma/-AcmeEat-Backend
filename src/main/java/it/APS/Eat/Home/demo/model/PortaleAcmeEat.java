package it.APS.Eat.Home.demo.model;

import com.couchbase.client.java.repository.annotation.Field;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

@Data
@Document
public class PortaleAcmeEat {

    @Id
    @GeneratedValue( strategy = GenerationStrategy.UNIQUE)
    private String codicePortale;

    @Field
    private String codiceCittaCorrente;

    @Field
    private String codiceDirettoreCorrente;

    @Field
    private String codiceRistoranteCorrente;

    @Field
    private String codiceMenuCorrente;

    @Field
    private String codiceConsumatoreCorrente;

    @Field
    private String codiceOrdinazioneCorrente;

    @Field
    private String codiceProdottoCorrente;
}
