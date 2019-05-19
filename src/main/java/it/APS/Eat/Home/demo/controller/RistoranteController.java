package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeServiceImpl;
import it.APS.Eat.Home.demo.service.Menu.MenuService;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/ristorante")
public class RistoranteController {
    
    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private AcmeHomeService acmeHomeService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProdottoService prodottoService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllRistoranti() {
        try {
            List<Ristorante> ristoranti = this.ristoranteService.getAllRistoranti();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Ristoranti", "--- OK --- Lista Ristoranti Trovata Con Successo").body(ristoranti);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getRistoranteByCodice/{codice}")
    private ResponseEntity getRistoranteByCodice(@PathVariable String codice) {
        try {
            Ristorante ristorante = ristoranteService.getRistoranteByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Ristorante Trovato", "--- OK --- Ristorante Trovato Con Successo").body(ristorante);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/setCittaDelRistorante/{codiceRistorante}")
    private ResponseEntity selezionaCittaDelRistorante(@PathVariable String codiceRistorante, @RequestBody String codiceCitta) {
        try {
            Ristorante ristorante = ristoranteService.selezionaCittaDelRistorante(codiceRistorante, codiceCitta);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Ristorante", "--- OK --- Ristorante Aggiornato Con Successo").body(ristorante);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/setDirettoreDelRistorante/{codiceRistorante}")
    private ResponseEntity aggiungiDirettoreDelRistorante(@PathVariable String codiceRistorante, @RequestBody String codiceDirettore) {
        try {
            Ristorante ristorante = ristoranteService.aggiungiDirettoreDelRistorante(codiceRistorante, codiceDirettore);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Ristorante", "--- OK --- Ristorante Aggiornato Con Successo").body(ristorante);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/setMenuDelRistorante/{codiceRistorante}")
    private ResponseEntity aggiungiMenuDelRistorante(@PathVariable String codiceRistorante, @RequestBody String codiceMenu) {
        try {
            Ristorante ristorante = ristoranteService.aggiungiMenuDelRistorante(codiceRistorante, codiceMenu);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Ristorante", "--- OK --- Ristorante Aggiornato Con Successo").body(ristorante);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/inserisciNuovoRistorante")
    public ResponseEntity inserisciNuovoRistorante() {
        try {
            List<Citta> citta = ristoranteService.inserisciNuovoRistorante();
            return ResponseEntity.status(HttpStatus.OK).header("Inizio Inserimento Ristorante", "--- OK --- Iniziata Creazione del Ristorante").body(citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertRistorante")
    public ResponseEntity aggiungiRistorante(@RequestBody Ristorante d) {
        try {
            ristoranteService.aggiungiRistorante(d);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Ristorante", "--- OK --- Ristorante Inserito Con Successo").body(getAllRistoranti().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateRistorante/{codice}")
    private ResponseEntity updateRistorante (@RequestBody Ristorante nuovoRistorante, @PathVariable String codice) {
        try {
            if (nuovoRistorante.getCodiceRistorante() != null) {
                nuovoRistorante.setCodiceRistorante(null);
            }
            ristoranteService.updateRistorante(nuovoRistorante, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Ristorante", "--- OK --- Ristorante Aggiornato Con Successo").body(getAllRistoranti().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteRistoranteByCodice/{codice}")
    private ResponseEntity deleteRistoranteByCodice(@PathVariable String codice) {
        try {
            ristoranteService.deleteRistoranteByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Ristorante", "--- OK --- Ristorante Eliminato Con Successo").body("Il Ristorante con codice: " + codice + " è stata Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/terminaInserimento/{codice}")
    private ResponseEntity terminaInserimento(@PathVariable String codice) {
        Citta cittaCorrente = null;
        Menu menuCorrente = null;
        try {
            Ristorante ristorante = ristoranteService.terminaInserimento(codice);
            menuCorrente = this.menuService.getMenuByCodice(ristorante.getCodiceMenu());
            for(Citta c : this.acmeHomeService.getAllCitta()) {
                if (c.getCodiceCitta().equals(ristorante.getCodiceCitta())) {
                    cittaCorrente = c;
                }
            }
            List<Prodotto> prodottiNelMenu = new ArrayList<>();
            for (String cp : menuCorrente.getCodiciProdotti()) {
                prodottiNelMenu.add(this.prodottoService.getProdottoByCodice(cp));
            }
            return ResponseEntity.status(HttpStatus.OK).header("Fine Inserimento Menu", "--- OK --- Hai completato l'insermento del Menu Con Successo")
                    .body("Il Menu con codice: " + ristorante.getCodiceMenu() + " Del Ristorante " + ristorante.getNome() +" è stato Completato con Successo\n\n"
                            + "Ristorante {\n\n" +
                            "Nome: " + ristorante.getNome() + "\n" +
                            "Descrizione: " + ristorante.getDescrizione() + "\n" +
                            "Città: " + cittaCorrente.getNome() + "\n\n" +
                            "Menu { \n\n" +  menuToString(prodottiNelMenu) + "}\n\n" + "}"
                    );
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
}
