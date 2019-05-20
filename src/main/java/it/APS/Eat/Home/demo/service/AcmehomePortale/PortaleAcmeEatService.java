package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.model.*;

public interface PortaleAcmeEatService {

    PortaleAcmeEat creaNuovoPortale();
    PortaleAcmeEat getPortale();
    AcmeHome getAzienda();
    Ristorante getRistoranteCorrente();
    Ristorante setRistoranteCorrente(String codice);
    Citta getCittaCorrente();
    Citta setCittaCorrente(String codice);
    Direttore getDirettoreCorrente();
    Direttore setDirettoreCorrente(String codice);
}