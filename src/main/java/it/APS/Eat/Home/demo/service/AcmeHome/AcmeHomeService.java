package it.APS.Eat.Home.demo.service.AcmeHome;

import it.APS.Eat.Home.demo.model.AcmeHome;

import java.util.List;

public interface AcmeHomeService {

    AcmeHome getAzienda();
    AcmeHome aggiungiAzienda(AcmeHome acmeHome);
    List<String> getAllCitta();
    List<String> getAllDirettori();
    AcmeHome confermaInserimentoRistorante(String codiceRistorante);
    AcmeHome disattivaRistorante(String codiceRistorante);
    List<String> getUtlimiQuindiciRistorantiInseriti();
    AcmeHome aggiornaAzienda(AcmeHome acmeHome);
}
