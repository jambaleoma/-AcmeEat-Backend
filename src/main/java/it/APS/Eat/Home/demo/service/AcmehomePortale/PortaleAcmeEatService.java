package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.model.*;

public interface PortaleAcmeEatService {

    PortaleAcmeEat creaNuovoPortale();
    PortaleAcmeEat getPortale();
    AcmeHome getAzienda();
    Ristorante getRistoranteCorrente();
    String setRistoranteCorrente(String codice);
    Citta getCittaCorrente();
    String setCittaCorrente(String codice);
    Direttore getDirettoreCorrente();
    String setDirettoreCorrente(String codice);
    Direttore loginDirettore(Direttore direttore);
    void logoutDirettore();
}