package it.APS.Eat.Home.demo.service.Citta;

import it.APS.Eat.Home.demo.model.Citta;

import java.util.List;

public interface CittaService {

    List<Citta> getAllCitta();
    Citta aggiungiCitta(Citta citta);
    Citta getCittaByCodice(String codice);
    Citta getCittaByName(String nomeCitta);
    Citta updateCitta(Citta nuovaCitta, String codice);
    Citta deleteCittaByCodice(String codice);
    Citta confermaInserimentoRistorante(String codiceRistorante);
    Citta disattivaRistorante(String codiceRistorante);

}
