package it.APS.Eat.Home.demo.service.Ristorante;

import it.APS.Eat.Home.demo.model.Ristorante;

import java.util.List;

public interface RistoranteService {

    List<Ristorante> getAllRistoranti();
    Ristorante aggiungiRistorante(Ristorante ristorante);
    Ristorante selezionaCittaDelRistorante(String codiceRistorante, String codiceCitta);
    Ristorante aggiungiDirettoreDelRistorante(String codiceRistorante, String codiceDirettore);
//    Ristorante aggiungiCucinaDelRistorante(String codiceRistorante, String codiceCucina);
//    Ristorante aggiungiOrdinazioneDelRistorante(String codiceRistorante, String codiceOrdinazione);
    Ristorante getRistoranteByCodice(String codice);
    Ristorante updateRistorante(Ristorante nuovoRistorante, String codice);
    Ristorante deleteRistoranteByCodice(String codice);
}
