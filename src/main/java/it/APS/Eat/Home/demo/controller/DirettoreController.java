package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Direttore;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/direttore")
public class DirettoreController {

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @Autowired
    private AcmeHomeService acmeHomeService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllDirettori() {
        try {
            List<Direttore> direttori = this.direttoreService.getAllDirettori();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Direttori", "--- OK --- Lista Direttori Trovata Con Successo").body(direttori);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getDirettoreByCodice/{codice}")
    private ResponseEntity getDirettoreByCodice(@PathVariable String codice) {
        try {
            Direttore direttore = direttoreService.getDirettoreByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Direttore Trovato", "--- OK --- Direttore Trovato Con Successo").body(direttore);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertDirettore")
    public ResponseEntity aggiungiDirettore(@RequestBody Direttore d) {
        try {
            direttoreService.aggiungiDirettore(d);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Direttore", "--- OK --- Direttore Inserito Con Successo").body(getAllDirettori().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateDirettore/{codice}")
    private ResponseEntity updateDirettore (@RequestBody Direttore nuovoDirettore, @PathVariable String codice) {
        try {
            if (nuovoDirettore.getCodiceDirettore() != null) {
                nuovoDirettore.setCodiceDirettore(null);
            }
            direttoreService.updateDirettore(nuovoDirettore, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Direttore", "--- OK --- Direttore Aggiornato Con Successo").body(getAllDirettori().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteDirettoreByCodice/{codice}")
    private ResponseEntity deleteDirettoreByCodice(@PathVariable String codice) {
        try {
            direttoreService.deleteDirettoreByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Direttore", "--- OK --- Direttore Eliminato Con Successo").body("Il Direttore con codice: " + codice + " è stata Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/loginDirettore")
    private ResponseEntity loginDirettore(@RequestBody Direttore direttore) {
        try {
            Direttore direttore1oggato = direttoreService.loginDirettore(direttore);
            return ResponseEntity.status(HttpStatus.OK).header("Login Direttore", "--- OK --- Direttore Loggato Con Successo")
                    .body("Il Direttore con codice: " + direttore1oggato.getCodiceDirettore() + " ha effettuato l'accesso\n\n"
                            + "Direttore\n" +
                            "Nome: " + direttore1oggato.getNome() + "\n" +
                            "Cognome: " + direttore1oggato.getCognome() + "\n" +
                            "Ristoranti: " + (direttore1oggato.getRistoranti() != null ? direttore1oggato.getRistoranti() : "nessun Ristorante" )
                    );
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/confermaInserimentoRistorante/{codiceRistorante}")
    private ResponseEntity confermaInserimentoRistorante(@PathVariable String codiceRistorante, @RequestBody Direttore direttoreCorrente) {
        try {
            Direttore direttore = direttoreService.confermaInserimentoRistorante(codiceRistorante, direttoreCorrente);
            Citta citta = cittaService.confermaInserimentoRistorante(codiceRistorante);
            AcmeHome acmeHome = acmeHomeService.confermaInserimentoRistorante(codiceRistorante);
            return ResponseEntity.status(HttpStatus.OK).header("Conferma Inserimento Ristorante", "--- OK --- Inserimento Ristorante Eseguito Con Successo")
                    .body("Direttore\n" +
                                "Nome: " + direttore.getNome() + "\n" +
                                "Cognome: " + direttore.getCognome() + "\n" +
                                "Ristoranti: " + (direttore.getRistoranti() != null ? ristoranteToString(direttore.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Citta\n" +
                                "Nome: " + citta.getNome() + "\n" +
                                "Ristoranti: " + (citta.getRistoranti() != null ? ristoranteToString(citta.getRistoranti()) : "nessun Ristorante" ) + "\n" +

                            "Azienda\n" +
                                "Ristoranti: " + (acmeHome.getQuindiciRistorantiRecenti() != null ? ristoranteToString(acmeHome.getQuindiciRistorantiRecenti()) : "nessun Ristorante" ) + "\n"
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
}
