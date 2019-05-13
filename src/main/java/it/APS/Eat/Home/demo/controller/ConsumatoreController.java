package it.APS.Eat.Home.demo.controller;
import it.APS.Eat.Home.demo.model.Consumatore;
import it.APS.Eat.Home.demo.service.Consumatore.ConsumatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/consumatore")
public class ConsumatoreController {

    @Autowired
    private ConsumatoreService consumatoreService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllConsumatori() {
        try {
            List<Consumatore> consumatori = this.consumatoreService.getAllConsumatori();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Consumatori", "--- OK --- Lista Consumatori Trovata Con Successo").body(consumatori);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getConsumatoreByCodice/{codice}")
    private ResponseEntity getConsumatoreByCodice(@PathVariable String codice) {
        try {
            Consumatore consumatore = consumatoreService.getConsumatoreByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Consumatore Trovato", "--- OK --- Consumatore Trovato Con Successo").body(consumatore);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertConsumatore")
    public ResponseEntity aggiungiConsumatore(@RequestBody Consumatore c) {
        try {
            consumatoreService.aggiungiConsumatore(c);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Consumatore", "--- OK --- Consumatore Inserito Con Successo").body(getAllConsumatori().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateConsumatore/{codice}")
    private ResponseEntity updateConsumatore (@RequestBody Consumatore nuovoConsumatore, @PathVariable String codice) {
        try {
            if (nuovoConsumatore.getCodiceConsumatore() != null) {
                nuovoConsumatore.setCodiceConsumatore(null);
            }
            consumatoreService.updateConsumatore(nuovoConsumatore, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Consumatore", "--- OK --- Consumatore Aggiornato Con Successo").body(getAllConsumatori().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteConsumatoreByCodice/{codice}")
    private ResponseEntity deleteConsumatoreByCodice(@PathVariable String codice) {
        try {
            consumatoreService.deleteConsumatoreByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Consumatore", "--- OK --- Consumatore Eliminato Con Successo").body("Il Consumatore con codice: " + codice + " è stata Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

}
