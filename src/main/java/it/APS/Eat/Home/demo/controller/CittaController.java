package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.service.CittaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/citta")
public class CittaController {

    @Autowired
    private CittaService cittaService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllCitta() {
        try {
            List<Citta> citta = this.cittaService.getAllCitta();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Citta", "--- OK --- Lista Citta Trovata Con Successo").body(citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getCittaByCodice/{codice}")
    private ResponseEntity getCittaByCodice(@PathVariable String codice) {
        try {
            Citta citta = this.cittaService.getCittaByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Citta Trovata", "--- OK --- Citta Trovata Con Successo").body(citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertCitta")
    public ResponseEntity aggiungiCitta(@RequestBody Citta c) {
        try {
            Citta cittaAggiunta = this.cittaService.aggiungiCitta(c);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Città", "--- OK --- Città Inserita Con Successo").body(cittaAggiunta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateCitta/{codice}")
    private ResponseEntity updateCitta (@RequestBody Citta nuovaCitta, @PathVariable String codice) {
        try {
            if (nuovaCitta.getCodice() != null) {
                nuovaCitta.setCodice(null);
            }
            Citta cittaAggiornata = cittaService.updateCitta(nuovaCitta, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Citta", "--- OK --- Citta Aggiornata Con Successo").body(getAllCitta().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteCittaByCodice/{codice}")
    private ResponseEntity deleteCittaByCodice(@PathVariable String codice) {
        try {
            cittaService.deleteCittaByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Citta", "--- OK --- Citta Eliminata Con Successo").body("La Citta con codice: " + codice + " è stato Eliminata con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

}
