package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.AcmehomePortale.PortaleAcmeEatService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
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
    private ProdottoService prodottoService;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @CrossOrigin
    @GetMapping(value = "/creaPortale")
    public ResponseEntity creaPortale() {
        try {
            this.portaleAcmeEatService.creaNuovoPortale();
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

    // Inzio Caso D'Uso UC1

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
                            "Ristoranti: \n" + (direttore1oggato.getRistoranti() != null ? ristoranteToString(direttore1oggato.getRistoranti()) : "nessun Ristorante" )
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/inserisciNuovoRistorante")
    public ResponseEntity inserisciNuovoRistorante() {
        try {
            List<Citta> citta = cittaService.getAllCitta();
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
    @PostMapping(value = "/insertProductToMenu/{codiceMenu}")
    public ResponseEntity aggiungiProdottoNelMenu(@PathVariable String codiceMenu, @RequestBody Prodotto ProdottoDaInserire) {
        try {
            Menu menu = menuService.aggiungiProdottoNelMenu(codiceMenu, ProdottoDaInserire);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Prodotto: " + ProdottoDaInserire.getCodiceProdotto() + " nel Menu " + codiceMenu, "--- OK --- Prodotto Inserito Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/terminaInserimento/{codice}")
    private ResponseEntity terminaInserimento(@PathVariable String codice) {
        Citta cittaCorrente = this.portaleAcmeEatService.getCittaCorrente();
        Ristorante ristoranteCorrente = this.portaleAcmeEatService.getRistoranteCorrente();
        try {
            Menu menuCorrente = this.menuService.terminaInserimento(codice);
            List<Prodotto> prodottiNelMenu = new ArrayList<>();
            for (String cp : menuCorrente.getCodiciProdotti()) {
                prodottiNelMenu.add(this.prodottoService.getProdottoByCodice(cp));
            }
            return ResponseEntity.status(HttpStatus.OK).header("Fine Inserimento Menu", "--- OK --- Hai completato l'insermento del Menu Con Successo")
                    .body("Il Menu con codice: " + codice + " Del Ristorante " + ristoranteCorrente.getNome() +" è stato Completato con Successo\n\n"
                            + "Ristorante {\n\n" +
                            "Nome: " + ristoranteCorrente.getNome() + "\n" +
                            "Descrizione: " + ristoranteCorrente.getDescrizione() + "\n" +
                            "Città: " + cittaCorrente.getNome() + "\n\n" +
                            "Menu { \n\n" +  menuToString(prodottiNelMenu) + "}\n\n" + "}"
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/selectSpecialProductToMenu/{codiceMenu}")
    public ResponseEntity selezionaProdottoSpecialeDelMenu(@PathVariable String codiceMenu, @RequestBody String codiceProdottoSpeciale) {
        try {
            Menu menu = menuService.selezionaProdottoSpecialeDelMenu(codiceMenu, codiceProdottoSpeciale);
            return ResponseEntity.status(HttpStatus.OK).header("Prodotto speciale: " + codiceProdottoSpeciale + " nel Menu: " + codiceMenu, "--- OK --- Prodotto speciale Salvato Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    private StringBuilder menuToString(List<Prodotto> prodotti) {
        StringBuilder listaProdotti = new StringBuilder();
        for (Prodotto p : prodotti) {
            String dettaglioProdotto = " Codice Prodotto: " + p.getCodiceProdotto() + "\n" +
                    " Nome Prodotto: " + p.getNome() + "\n" +
                    " Descrizone: " + p.getDescrizione() + "\n" +
                    " Prezzo: " + p.getPrezzo() + "\n\n";
            listaProdotti.append(dettaglioProdotto);
        }
        return listaProdotti;
    }

    @CrossOrigin
    @PutMapping(value = "/confermaInserimentoRistorante/{codiceRistorante}")
    private ResponseEntity confermaInserimentoRistorante(@PathVariable String codiceRistorante) {
        try {
            Ristorante ristorante = this.ristoranteService.getRistoranteByCodice(codiceRistorante);
            Direttore direttore =  this.direttoreService.confermaInserimentoRistorante(ristorante.getCodiceRistorante(), ristorante.getCodiceDirettore());
            Citta citta = cittaService.confermaInserimentoRistorante(ristorante.getCodiceRistorante());
            AcmeHome acmeHome = acmeHomeService.addRistoranteToUltimi15(ristorante.getCodiceRistorante());
            return ResponseEntity.status(HttpStatus.OK).header("Conferma Inserimento Ristorante", "--- OK --- Inserimento Ristorante Eseguito Con Successo")
                    .body("Direttore\n" +
                            "Nome: " + direttore.getNome() + "\n" +
                            "Cognome: " + direttore.getCognome() + "\n" +
                            "Ristoranti:\n " + (direttore.getRistoranti() != null ? ristoranteToString(direttore.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Citta\n" +
                            "Nome: " + citta.getNome() + "\n" +
                            "Ristoranti:\n " + (citta.getRistoranti() != null ? ristoranteToString(citta.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Azienda\n" +
                            "Ristoranti:\n " + (acmeHome.getQuindiciRistorantiRecenti() != null ? ristoranteToString(acmeHome.getQuindiciRistorantiRecenti()) : "nessun Ristorante" ) + "\n"
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    private StringBuilder ristoranteToString(List<Ristorante> ristoranti) {
        StringBuilder listaRistoranti = new StringBuilder();
        for (Ristorante r : ristoranti) {
            String dettaglioRistorante =
                    " Codice Ristorante: " + r.getCodiceRistorante() + "\n" +
                            " Nome Ristorante: " + r.getNome() + "\n" +
                            " Descrizone: " + r.getDescrizione() + "\n" +
                            " codiceCitta: " + r.getCodiceCitta() + "\n" +
                            " codiceDirettore: " + r.getCodiceDirettore() + "\n" +
                            " codiceMenu: " + r.getCodiceMenu() + "\n\n";
            listaRistoranti.append(dettaglioRistorante);
        }
        return listaRistoranti;
    }

    // Fine Caso D'Uso UC1

    // Inzio Caso D'Uso UC2

    @CrossOrigin
    @PostMapping(value = "/loginConsumatore")
    private ResponseEntity loginConsumatore(@RequestBody Consumatore consumatore) {
        try {
            Consumatore consumatoreLoggato = this.portaleAcmeEatService.loginConsumatore(consumatore);
            return ResponseEntity.status(HttpStatus.OK).header("Login Consumatore", "--- OK --- Consumatore Loggato Con Successo")
                    .body("Il Consumatore con Email: " + consumatoreLoggato.getEmail() + " ha effettuato l'accesso\n\n" +
                            "Consumatore\n" +
                            "Nome: " + consumatoreLoggato.getNome() + "\n" +
                            "Cognome: " + consumatoreLoggato.getCognome() + "\n" +
                            "N.Cellulare: " + consumatoreLoggato.getCellulare()  + "\n"
                    );
        } catch (Exception e) {
            throw e;
        }
    }
}
