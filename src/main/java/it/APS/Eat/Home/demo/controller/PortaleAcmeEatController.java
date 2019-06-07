package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.AcmehomePortale.PortaleAcmeEatService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Consumatore.ConsumatoreService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import it.APS.Eat.Home.demo.service.Menu.MenuService;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/portaleAcmeEat")
public class PortaleAcmeEatController {

    @Autowired
    private AcmeHomeService acmeHomeService;

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ConsumatoreService consumatoreService;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @CrossOrigin
    @PostMapping(value = "/creaPortale")
    public ResponseEntity creaPortale(@RequestBody PortaleAcmeEat portaleAcmeEat) {
        try {
            this.portaleAcmeEatService.creaNuovoPortale(portaleAcmeEat);
            return ResponseEntity.status(HttpStatus.OK).header("Inizio Creazione Portale", "--- OK --- Nuovo Portale Creato con Successo").body("Nuovo Portale Creato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getPortale")
    public ResponseEntity getPortale() {
        try {
            PortaleAcmeEat p = this.portaleAcmeEatService.getPortale();
            return ResponseEntity.status(HttpStatus.OK).header("Sessione Portale", "--- OK --- Sessione Portale Trovata con Successo").body(p);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/logoutDirettore")
    private ResponseEntity logoutDirettore() {
        try {
            portaleAcmeEatService.logoutDirettore();
            return ResponseEntity.status(HttpStatus.OK).header("Logout Direttore", "--- OK --- Uscita effettuata Con Successo").body("Uscita effettuata Con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/logoutConsumatore")
    private ResponseEntity logoutConsumatore() {
        try {
            portaleAcmeEatService.logoutConsumatore();
            return ResponseEntity.status(HttpStatus.OK).header("Logout Consumatore", "--- OK --- Uscita effettuata Con Successo").body("Uscita effettuata Con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    // Caso D'Uso UC1

    @CrossOrigin
    @PostMapping(value = "/loginDirettore")
    private ResponseEntity loginDirettore(@RequestBody Direttore direttore) {
        try {
            Direttore direttore1oggato = this.portaleAcmeEatService.loginDirettore(direttore);
            return ResponseEntity.status(HttpStatus.OK).header("Login Direttore", "--- OK --- Direttore Loggato Con Successo")
                    .body("Il Direttore con codice: " + direttore1oggato.getCodiceDirettore() + " ha effettuato l'accesso\n\n"
                            + "Direttore\n" +
                            "Nome: " + direttore1oggato.getNome() + "\n" +
                            "Cognome: " + direttore1oggato.getCognome() + "\n" +
                            "Ristoranti: \n" + (direttore1oggato.getRistoranti() != null ? ristorantiToString(direttore1oggato.getRistoranti()) : "nessun Ristorante" )
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/inserisciNuovoRistorante")
    public ResponseEntity inserisciNuovoRistorante() {
        try {
            List<String> citta = this.portaleAcmeEatService.getNomiCitta();
            return ResponseEntity.status(HttpStatus.OK).header("Inizio Inserimento Ristorante", "--- OK --- Iniziata Creazione del Ristorante").body(citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/creaNuovoRistorante")
    public ResponseEntity creaNuovoRistorante(@RequestBody Ristorante d) {
        try {
            Ristorante ristorante = ristoranteService.aggiungiRistorante(d);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Ristorante", "--- OK --- Ristorante Inserito Con Successo").body(ristorante);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertProductToMenu")
    public ResponseEntity aggiungiProdottoNelMenu(@RequestBody Prodotto prodottoDaInserire) {
        try {
            Menu menu = menuService.aggiungiProdottoNelMenu(prodottoDaInserire);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Prodotto: " + prodottoDaInserire.getCodiceProdotto() + " nel Menu " + portaleAcmeEatService.getMenuCorrente().getCodiceMenu(), "--- OK --- Prodotto Inserito Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/terminaInserimento")
    private ResponseEntity terminaInserimento() {
        Citta cittaCorrente = this.portaleAcmeEatService.getCittaCorrente();
        Ristorante ristoranteCorrente = this.portaleAcmeEatService.getRistoranteCorrente();
        Menu menuCorrente = this.portaleAcmeEatService.getMenuCorrente();
        try {
            return ResponseEntity.status(HttpStatus.OK).header("Fine Inserimento Menu", "--- OK --- Hai completato l'insermento del Menu Con Successo")
                    .body("Il Menu con codice: " + menuCorrente.getCodiceMenu() + " Del Ristorante " + ristoranteCorrente.getNome() +" è stato Completato con Successo\n\n"
                            + "Ristorante {\n\n" +
                            "Nome: " + ristoranteCorrente.getNome() + "\n" +
                            "Descrizione: " + ristoranteCorrente.getDescrizione() + "\n" +
                            "Città: " + cittaCorrente.getNome() + "\n\n" +
                            "Menu { \n\n" +  menuToString(menuCorrente) + "}\n\n" + "}"
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/selectSpecialProductToMenu/{codiceProdottoSpeciale}")
    public ResponseEntity selezionaProdottoSpecialeDelMenu(@PathVariable String codiceProdottoSpeciale) {
        try {
            Menu menu = menuService.selezionaProdottoSpecialeDelMenu(codiceProdottoSpeciale);
            return ResponseEntity.status(HttpStatus.OK).header("Prodotto speciale: " + codiceProdottoSpeciale + " nel Menu: " + portaleAcmeEatService.getMenuCorrente().getCodiceMenu(), "--- OK --- Prodotto speciale Salvato Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/confermaInserimentoRistorante")
    private ResponseEntity confermaInserimentoRistorante() {
        try {
            Ristorante ristorante = this.portaleAcmeEatService.getRistoranteCorrente();
            Direttore direttore =  this.direttoreService.confermaInserimentoRistorante(ristorante.getCodiceRistorante(), ristorante.getCodiceDirettore());
            Citta citta = this.cittaService.confermaInserimentoRistorante(ristorante.getCodiceRistorante());
            AcmeHome acmeHome = acmeHomeService.confermaInserimentoRistorante(ristorante.getCodiceRistorante());

            return ResponseEntity.status(HttpStatus.OK).header("Conferma Inserimento Ristorante", "--- OK --- Inserimento Ristorante Eseguito Con Successo")
                    .body("Direttore\n" +
                            "Nome: " + direttore.getNome() + "\n" +
                            "Cognome: " + direttore.getCognome() + "\n" +
                            "Ristoranti:\n " + (direttore.getRistoranti() != null ? ristorantiToString(direttore.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Citta\n" +
                            "Nome: " + citta.getNome() + "\n" +
                            "Ristoranti:\n " + (citta.getRistoranti() != null ? ristorantiToString(citta.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Azienda\n" +
                            "Ristoranti:\n " + (acmeHome.getCodiciQuindiciRistorantiRecenti() != null ? codiciRistorantiToString(acmeHome.getCodiciQuindiciRistorantiRecenti()) : "nessun Ristorante" ) + "\n"
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    // Caso D'Uso UC2

    @CrossOrigin
    @PostMapping(value = "/loginConsumatore")
    private ResponseEntity loginConsumatore(@RequestBody Consumatore consumatore) {
        try {
            Consumatore consumatoreLoggato = this.portaleAcmeEatService.loginConsumatore(consumatore);
            List<String> nuoviRistoranti = this.acmeHomeService.getUtlimiQuindiciRistorantiInseriti();
            return ResponseEntity.status(HttpStatus.OK).header("Login Consumatore", "--- OK --- Consumatore Loggato Con Successo")
                    .body("Il Consumatore con Email: " + consumatoreLoggato.getEmail() + " ha effettuato l'accesso\n\n" +
                            "Consumatore\n" +
                            "Nome: " + consumatoreLoggato.getNome() + "\n" +
                            "Cognome: " + consumatoreLoggato.getCognome() + "\n\n" +
                            "Ultimi 15 Ristoranti: \n" + codiciRistorantiToString(nuoviRistoranti) + "\n"

                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/inserisciNuovaOrdinazione")
    public ResponseEntity inserisciNuovaOrdinazione() {
        try {
            List<String> citta = this.portaleAcmeEatService.getNomiCitta();
            return ResponseEntity.status(HttpStatus.OK).header("Inizio Inserimento Ordinazione", "--- OK --- Iniziata Ordinazione del Consumatore").body("Lista Citta: \n" + citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/selezionaCitta/{nomeCitta}")
    public ResponseEntity selezionaCitta(@PathVariable String nomeCitta) {
        try {
            List<Ristorante> ristoranti = this.portaleAcmeEatService.getRistorantiInCitta(nomeCitta);
            return ResponseEntity.status(HttpStatus.OK).header("Selezione Citta", "--- OK --- Citta Selezionata Con Successo").body("Ristoranti Presenti a " + nomeCitta + ": \n" + ristorantiToString(ristoranti));
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/selezionaRistorante/{codiceRistorante}")
    public ResponseEntity selezionaRistorante(@PathVariable String codiceRistorante) {
        try {
            Ristorante ristorante = this.portaleAcmeEatService.selezionaRistorante(codiceRistorante);
            Direttore direttore = this.direttoreService.getDirettoreByCodice(ristorante.getCodiceDirettore());
            Menu menu = this.menuService.getMenuByCodice(ristorante.getCodiceMenu());
            Prodotto specialita = this.prodottoService.getProdottoByCodice(menu.getSpecialita());
            return ResponseEntity.status(HttpStatus.OK).header("Selezione Ristorante", "--- OK --- Ristorante Selezionato Con Successo").body(
                    "Ristorante: \n" +
                            "Nome: " + ristorante.getNome() + "\n" +
                            "Descrizione: " + ristorante.getDescrizione() + "\n\n" +
                            "Direttore del Ristorante: \n" +
                            "Nome: " + direttore.getNome() + "\n" +
                            "Cognome: " + direttore.getCognome() + "\n\n" +
                            "Menù: \n" + menuToString(menu) + "\n" +
                            "Specialità del Menù: \n" +
                            " Codice Prodotto: " + specialita.getCodiceProdotto() + "\n" +
                            " Nome: " + specialita.getNome() + "\n" +
                            " Descrizione: " + specialita.getDescrizione() + "\n" +
                            " Prezzo: " + specialita.getPrezzo() + "€\n"
            );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertProductToOrdinazione")
    public ResponseEntity insertProductToOrdinazione(@RequestBody RigaOrdinazione ro) {
        try {
            Prodotto prodottoDaInserire = this.portaleAcmeEatService.aggiungiProdottoNellaOrdinazione(ro);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Prodotto " + prodottoDaInserire.getNome() + " nell'ordinazione", "--- OK --- Prodotto Inserito Con Successo").body(
                    "Prodotto: \n" +
                            "Descrizione: " + prodottoDaInserire.getNome() + "\n" +
                            "Prezzo al dettaglio: " + prodottoDaInserire.getPrezzo() + "€\n");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/terminaInserimentoOrdinazione")
    private ResponseEntity terminaInserimentoOrdinazione() {
        try {
            Ordinazione ordinazione = this.portaleAcmeEatService.getTotaleOrdinazione();
            return ResponseEntity.status(HttpStatus.OK).header("Fine Inserimento Ordinazione", "--- OK --- Hai completato l'insermento dell'Ordinzione Con Successo").body(
                    "Riepilogo Ordinazione: \n\n" + ordinazioneToString(ordinazione));
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/confermaOrdinazione")
    private ResponseEntity confermaOrdinazione(@RequestBody Ordinazione indirizzo) {
        try {
            Ordinazione ordinazione = this.portaleAcmeEatService.confermaOrdinazione(indirizzo);
            Consumatore consumatore = this.consumatoreService.getConsumatoreByCodice(ordinazione.getCodiceConsumatore());
            Ristorante ristorante = this.ristoranteService.getRistoranteByCodice(ordinazione.getCodiceRistorante());
            return ResponseEntity.status(HttpStatus.OK).header("Conferma Inserimento Ristorante", "--- OK --- Inserimento Ristorante Eseguito Con Successo")
                    .body(
                            "Ordinazione Effettuata:\n " + ordinazioneToString(ordinazione) + "\n\n" +
                                    "Consumatore: \n" +
                                    "Nome: " + consumatore.getNome() + "\n" +
                                    "Cognome: " + consumatore.getCognome() + "\n" +
                                    "Lista Ordinazioni: " + consumatore.getCodiciOrdinazioni() + "\n" +

                                    "Ristorante\n" +
                                    "Nome: " + ristorante.getNome() + "\n" +
                                    "Descrizione: " + ristorante.getDescrizione() + "\n" +
                                    "Lista Ordinazioni: " + ristorante.getCodiciOrdinazioni() + "\n"
                    );
        } catch (Exception e) {
            throw e;
        }
    }



    // Metodi ToString

    private StringBuilder ristorantiToString(List<Ristorante> ristoranti) {
        StringBuilder listaRistoranti = new StringBuilder();
        for (Ristorante r : ristoranti) {
            String dettaglioRistorante =
                    (r.getCodiceRistorante() != null ?  " Codice Ristorante: " + r.getCodiceRistorante() : "" ) + "\n" +
                            " Nome Ristorante: " + r.getNome() + "\n" +
                            " Descrizone: " + r.getDescrizione() + "\n" +
                            " codiceCitta: " + r.getCodiceCitta() + "\n" +
                            " codiceDirettore: " + r.getCodiceDirettore() + "\n" +
                            " codiceMenu: " + r.getCodiceMenu() + "\n\n";
            listaRistoranti.append(dettaglioRistorante);
        }
        return listaRistoranti;
    }

    public StringBuilder codiciRistorantiToString(List<String> codiciRistoranti) {
        List<Ristorante> ristoranti = new ArrayList<>();
        for (String cr : codiciRistoranti) {
            ristoranti.add(this.ristoranteService.getRistoranteByCodice(cr));
        }
        return ristorantiToString(ristoranti);
    }

    private StringBuilder menuToString(Menu menu) {
        List<Prodotto> prodottiNelMenu = new ArrayList<>();
        for (String cp : menu.getCodiciProdotti()) {
            prodottiNelMenu.add(this.prodottoService.getProdottoByCodice(cp));
        }
        StringBuilder listaProdotti = new StringBuilder();
        for (Prodotto p : prodottiNelMenu) {
            String dettaglioProdotto = " Codice Prodotto: " + p.getCodiceProdotto() + "\n" +
                    " Nome Prodotto: " + p.getNome() + "\n" +
                    " Descrizone: " + p.getDescrizione() + "\n" +
                    " Prezzo: " + p.getPrezzo() + "€\n\n";
            listaProdotti.append(dettaglioProdotto);
        }
        return listaProdotti;
    }

    private StringBuilder ordinazioneToString(Ordinazione ordinazione) {
        StringBuilder listaProdotti = new StringBuilder();
        for (RigaOrdinazione ro : ordinazione.getRigheOrdinazione()) {
            Prodotto p = this.prodottoService.getProdottoByCodice(ro.getCodiceProdotto());
            String dettaglioProdotto =
                    " Nome Prodotto: " + p.getNome() + "\n" +
                            " Descrizone: " + p.getDescrizione() + "\n" +
                            " Quantità: " + ro.getQuantita() + "\n" +
                            " Nota: " + (ro.getNota() != null ? ro.getNota() : "") + "\n" +
                            " Prezzo: " + ro.getTotaleRiga() + "€\n\n";
            listaProdotti.append(dettaglioProdotto);
        }
        String totaleOrdinazione = "------------------------------------------------\n               Totale: " + ordinazione.getTotale() + " €\n";
        listaProdotti.append(totaleOrdinazione);
        return listaProdotti;
    }
}
