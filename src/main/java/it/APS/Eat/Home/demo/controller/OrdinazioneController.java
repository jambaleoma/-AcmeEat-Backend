package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.Ordinazione;
import it.APS.Eat.Home.demo.service.Ordinazione.OrdinazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/ordinazione")
public class OrdinazioneController {

    @Autowired
    private OrdinazioneService ordinazioneService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllOrdinazioni() {
        try {
            List<Ordinazione> ordinazione = this.ordinazioneService.getAllOrdinazioni();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Ordinazioni", "--- OK --- Lista Ordinazioni Trovata Con Successo").body(ordinazione);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getOrdinazioneByCodice/{codice}")
    private ResponseEntity getOrdinazioneByCodice(@PathVariable String codice) {
        try {
            Ordinazione ordinazione = ordinazioneService.getOrdinazioneByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Ordinazione Trovata", "--- OK --- Ordinazione Trovata Con Successo").body(ordinazione);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertOrdinazione")
    public ResponseEntity aggiungiOrdinazione(@RequestBody Ordinazione o) {
        try {
            ordinazioneService.aggiungiOrdinazione(o);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Ordinazione", "--- OK --- Ordinazione Inserita Con Successo").body(getAllOrdinazioni().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateOrdinazione/{codice}")
    private ResponseEntity updateOrdinazione (@RequestBody Ordinazione nuovaOrdinazione, @PathVariable String codice) {
        try {
            ordinazioneService.updateOrdinazione(nuovaOrdinazione, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Ordinazione", "--- OK --- Ordinazione Aggiornata Con Successo").body(getAllOrdinazioni().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteOrdinazioneByCodice/{codice}")
    private ResponseEntity deleteOrdinazioneByCodice(@PathVariable String codice) {
        try {
            ordinazioneService.deleteOrdinazioneByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Ordinazione", "--- OK --- Ordinazione Eliminata Con Successo").body("L'Ordinazione con codice: " + codice + " è stata Eliminata con Successo");
        } catch (Exception e) {
            throw e;
        }
    }
}
