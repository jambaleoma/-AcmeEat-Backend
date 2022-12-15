package it.APS.Eat.Home.demo.service.Ristorante;

import it.APS.Eat.Home.demo.model.Ristorante;

import java.util.ArrayList;
import java.util.List;

public interface RistoranteService {

    List<Ristorante> getAllRistoranti();
    Ristorante aggiungiRistorante(Ristorante ristorante);
    Ristorante getRistoranteByCodice(String codice);
    ArrayList<Ristorante> getRistorantiByCodiceDirettore(String codiceDirettore);
    Ristorante updateRistorante(Ristorante nuovoRistorante, String codice);
    Ristorante deleteRistoranteByCodice(String codice);
}
