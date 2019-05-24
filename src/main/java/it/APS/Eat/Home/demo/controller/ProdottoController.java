package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.Prodotto;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/prodotto")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllProdotti() {
        try {
            List<Prodotto> prodotti = this.prodottoService.getAllProdotti();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Prodotti", "--- OK --- Lista Prodotti Trovata Con Successo").body(prodotti);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getProdottoByCodice/{codice}")
    private ResponseEntity getProdottoByCodice(@PathVariable String codice) {
        try {
            Prodotto prodotto = prodottoService.getProdottoByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Prodotto Trovato", "--- OK --- Prodotto Trovato Con Successo").body(prodotto);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertProdotto")
    public ResponseEntity aggiungiProdotto(@RequestBody Prodotto c) {
        try {
            prodottoService.aggiungiProdotto(c);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Prodotto", "--- OK --- Prodotto Inserito Con Successo").body(getAllProdotti().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateProdotto/{codice}")
    private ResponseEntity updateProdotto (@RequestBody Prodotto nuovoProdotto, @PathVariable String codice) {
        try {
            prodottoService.updateProdotto(nuovoProdotto, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Prodotto", "--- OK --- Prodotto Aggiornato Con Successo").body(getAllProdotti().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteProdottoByCodice/{codice}")
    private ResponseEntity deleteProdottoByCodice(@PathVariable String codice) {
        try {
            prodottoService.deleteProdottoByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Prodotto", "--- OK --- Prodotto Eliminato Con Successo").body("Il Prodotto con codice: " + codice + " è stata Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }
}
