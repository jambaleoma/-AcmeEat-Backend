package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/acmehome")
public class AcmeHomeController {

    @Autowired
    private AcmeHomeService acmeHomeService;

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
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Azienda", "--- OK --- Azienda Inserita Con Successo").body(acmeHome);
        } catch (Exception e) {
            throw e;
        }
    }
}
