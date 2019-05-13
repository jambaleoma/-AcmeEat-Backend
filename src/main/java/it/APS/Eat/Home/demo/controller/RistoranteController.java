package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/ristorante")
public class RistoranteController {
    
    @Autowired
    private RistoranteService ristoranteService;

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
}
