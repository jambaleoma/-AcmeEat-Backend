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
}
