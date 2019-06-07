package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.model.*;

import java.util.List;

public interface PortaleAcmeEatService {

    PortaleAcmeEat creaNuovoPortale(PortaleAcmeEat portaleAcmeEat);
    PortaleAcmeEat getPortale();
    Ristorante getRistoranteCorrente();
    String setRistoranteCorrente(String codice);
    Citta getCittaCorrente();
    String setCittaCorrente(String codice);
    Direttore getDirettoreCorrente();
    String setDirettoreCorrente(String codice);
    Menu getMenuCorrente();
    String setMenuCorrente(String codice);
    Consumatore getConsumatoreCorrente();
    String setConsumatoreCorrente(String codice);
    Direttore loginDirettore(Direttore direttore);
    Consumatore loginConsumatore(Consumatore consumatore);
    List<String> getNomiCitta();
    List<Ristorante> getRistorantiInCitta(String nomeCitta);
    Ristorante selezionaRistorante(String codiceRistorante);
    Ordinazione getOrdinazioneCorrente();
    String setOrdinazioneCorrente(String codice);
    Prodotto getProdottoCorrente();
    String setProdottoCorrente(String codice);
    Prodotto aggiungiProdottoNellaOrdinazione(RigaOrdinazione rigaOrdinazione);
    Ordinazione getTotaleOrdinazione();
    Ordinazione confermaOrdinazione(Ordinazione indirizzo);
    void logoutDirettore();
    void logoutConsumatore();
}