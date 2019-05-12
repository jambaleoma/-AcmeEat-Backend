package it.APS.Eat.Home.demo.service;

import it.APS.Eat.Home.demo.model.Citta;

import java.util.List;
import java.util.Optional;

public interface CittaService {

    List<Citta> getAllCitta();
    Citta aggiungiCitta(Citta citta);
    Citta getCittaByCodice(String codice);
    Citta updateCitta(Citta nuovaCitta, String codice);
    Citta deleteCittaByCodice(String codice);

}
