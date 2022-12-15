package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
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
    @GetMapping(value = "/getRistorantiByCodiceDirettore/{codiceDirettore}")
    private ResponseEntity getRistorantiByCodiceDirettore(@PathVariable String codiceDirettore) {
        ResponseMessage response = new ResponseMessage();
        try {
            ArrayList<Ristorante> ristoranti = ristoranteService.getRistorantiByCodiceDirettore(codiceDirettore);
            response.setResponse(ristoranti);
            response.setMessage("Ristoranti Trovati Con Successo");
            return ResponseEntity.status(HttpStatus.OK).header("Ristoranti Trovati", "--- OK --- Ristoranti Trovati Con Successo").body(response);
        } catch (Exception e) {
            if (e.getClass().equals(NotFoundException.class)) {
                ResponseMessage errorResp = new ResponseMessage();
                errorResp.setResponse(false);
                errorResp.setMessage("Nessun ristorante trovato");
                return ResponseEntity.status(HttpStatus.OK).header("Ricerca ristorante", "--- OK --- Nessun ristorante trovato")
                        .body(errorResp);
            }
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
            ResponseMessage resp = new ResponseMessage();
            resp.setMessage("Il Ristorante con codice: " + codice + " è stata Eliminato con Successo");
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Ristorante", "--- OK --- Ristorante Eliminato Con Successo").body(resp);
        } catch (Exception e) {
            throw e;
        }
    }
}
