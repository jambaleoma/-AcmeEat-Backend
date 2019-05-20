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
@RequestMapping("/rest/acmehome")
public class AcmeHomeController {

    @Autowired
    private AcmeHomeService acmeHomeService;

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @CrossOrigin
    @PostMapping(value = "/aggingiAzienda")
    public ResponseEntity aggingiAzienda(@RequestBody AcmeHome acmeHome) {
        try {
            acmeHomeService.aggingiAzienda(acmeHome);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Azienda", "--- OK --- Azienda Inserita Con Successo").body(acmeHome);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getAzienda")
    public ResponseEntity getAzienda() {
        try {
            AcmeHome acmeHome = acmeHomeService.getAzienda();
            acmeHome.setDirettori(direttoreService.getAllDirettori());
            acmeHome.setCitta(cittaService.getAllCitta());
            return ResponseEntity.status(HttpStatus.OK).header("Azienda", "--- OK --- Azienda Trovata Con Successo").body(acmeHome);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getCitta")
    public ResponseEntity getCitta() {
        try {
            List<Citta> citta = cittaService.getAllCitta();
            return ResponseEntity.status(HttpStatus.OK).header("Lista delle Citta", "--- OK --- Lista delle Citta Trovata Con Successo").body(citta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getDirettori")
    public ResponseEntity getDirettori() {
        try {
            List<Direttore> direttori = direttoreService.getAllDirettori();
            return ResponseEntity.status(HttpStatus.OK).header("Lista dei Direttori", "--- OK --- Lista dei Direttori Trovata Con Successo").body(direttori);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getUtlimiQuindiciRistorantiInseriti")
    public ResponseEntity getUtlimiQuindiciRistorantiInseriti() {
        try {
            List<Ristorante> ultimi15Ristoranti = acmeHomeService.getUtlimiQuindiciRistorantiInseriti();
            return ResponseEntity.status(HttpStatus.OK).header("Lista dei Direttori", "--- OK --- Lista dei Direttori Trovata Con Successo").body(ultimi15Ristoranti);
        } catch (Exception e) {
            throw e;
        }
    }
}