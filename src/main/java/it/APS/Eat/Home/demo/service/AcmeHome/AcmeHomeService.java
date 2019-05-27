package it.APS.Eat.Home.demo.service.AcmeHome;

import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Direttore;
import it.APS.Eat.Home.demo.model.Ristorante;

import java.util.List;

public interface AcmeHomeService {

    AcmeHome getAzienda();
    AcmeHome aggingiAzienda(AcmeHome acmeHome);
    List<String> getUtlimiQuindiciRistorantiInseriti();
    List<Citta> getAllCitta();
    List<Direttore> getAllDirettori();
    AcmeHome addRistoranteToUltimi15(String codiceRistorante);
}
