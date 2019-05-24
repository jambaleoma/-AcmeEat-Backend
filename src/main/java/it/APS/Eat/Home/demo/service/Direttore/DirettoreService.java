package it.APS.Eat.Home.demo.service.Direttore;

import it.APS.Eat.Home.demo.model.Direttore;

import java.util.List;

public interface DirettoreService {

    List<Direttore> getAllDirettori();
    Direttore aggiungiDirettore(Direttore direttore);
    Direttore getDirettoreByCodice(String codice);
    Direttore updateDirettore(Direttore nuovoDirettore, String codice);
    Direttore deleteDirettoreByCodice(String codice);
    Direttore confermaInserimentoRistorante(String codiceRistorante,  String codiceDirettore);
    Direttore checkPassword(Direttore direttore, String psw);
}
