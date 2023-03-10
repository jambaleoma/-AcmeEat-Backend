package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Consumatore.ConsumatoreService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
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

    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private ConsumatoreService consumatoreService;

    @Autowired
    private PortaleAcmeEatController portaleAcmeEatController;

    @CrossOrigin
    @PostMapping(value = "/aggiungiAzienda")
    public ResponseEntity aggingiAzienda(@RequestBody AcmeHome acmeHome) {
        try {
            acmeHomeService.aggiungiAzienda(acmeHome);
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
            return ResponseEntity.status(HttpStatus.OK).header("Azienda", "--- OK --- Azienda Trovata Con Successo").body(acmeHome);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getCitta")
    public ResponseEntity getCitta() {
        try {
            List<String> codiciCitta = acmeHomeService.getAllCitta();
            return ResponseEntity.status(HttpStatus.OK).header("Lista delle Citta", "--- OK --- Lista delle Citta Trovata Con Successo").body(codiciCitta);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getDirettori")
    public ResponseEntity getDirettori() {
        try {
            List<String> codiciDirettori = acmeHomeService.getAllDirettori();
            return ResponseEntity.status(HttpStatus.OK).header("Lista dei Direttori", "--- OK --- Lista dei Direttori Trovata Con Successo").body(codiciDirettori);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getUtlimiQuindiciRistorantiInseriti")
    public ResponseEntity getUtlimiQuindiciRistorantiInseriti() {
        try {
            List<String> ultimi15Ristoranti = acmeHomeService.getUtlimiQuindiciRistorantiInseriti();
            return ResponseEntity.status(HttpStatus.OK).header("Lista dei Direttori", "--- OK --- Lista dei Direttori Trovata Con Successo").body(
                    "Ultimi 15 Ristoranti Inseriti: \n\n" + ultimi15Ristoranti);
        } catch (Exception e) {
            throw e;
        }
    }
}