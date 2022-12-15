package it.APS.Eat.Home.demo.controller;

import it.APS.Eat.Home.demo.model.Menu;
import it.APS.Eat.Home.demo.model.Prodotto;
import it.APS.Eat.Home.demo.model.ResponseMessage;
import it.APS.Eat.Home.demo.service.Menu.MenuService;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProdottoService prodottoService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllMenu() {
        try {
            List<Menu> menu = this.menuService.getAllMenu();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Menu", "--- OK --- Lista Menu Trovata Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getMenuByCodice/{codice}")
    private ResponseEntity getMenuByCodice(@PathVariable String codice) {
        try {
            Menu menu = menuService.getMenuByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Menu Trovato", "--- OK --- Menu Trovato Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getAllProductsByMenu/{codiceMenu}")
    private ResponseEntity getAllProductsByMenu(@PathVariable String codiceMenu) {
        try {
            ArrayList<Prodotto> prodotti = menuService.getAllProductsByMenu(codiceMenu);
            ResponseMessage resp = new ResponseMessage();
            resp.setResponse(prodotti);
            resp.setMessage("Elenco Prodotti del Menù: " + codiceMenu );
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Menu", "--- OK --- Menu Eliminato Con Successo").body(resp);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertProductToMenu")
    public ResponseEntity aggiungiProdottoNelMenu(@RequestBody Prodotto prodottoDaInserire) {
        try {
            Menu menu = menuService.aggiungiProdottoNelMenu(prodottoDaInserire);
            return ResponseEntity.status(HttpStatus.OK).header("--- OK --- Prodotto Inserito Con Successo").body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertMenu")
    public ResponseEntity aggiungiMenu(@RequestBody Menu d) {
        try {
            menuService.aggiungiMenu(d);
            return ResponseEntity.status(HttpStatus.OK).header("Inserimento Menu", "--- OK --- Menu Inserito Con Successo").body(getAllMenu().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteProductToMenu/{codiceMenu}")
    public ResponseEntity eliminaProdottoNelMenu(@PathVariable String codiceMenu, @RequestBody String codiceProdottoDaEliminare) {
        try {
            Menu menu = menuService.eliminaProdottoNelMenu(codiceMenu, codiceProdottoDaEliminare);
            return ResponseEntity.status(HttpStatus.OK).body(menu);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/updateMenu/{codice}")
    private ResponseEntity updateMenu (@RequestBody Menu nuovoMenu, @PathVariable String codice) {
        try {
            menuService.updateMenu(nuovoMenu, codice);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Menu", "--- OK --- Menu Aggiornato Con Successo").body(getAllMenu().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteMenuByCodice/{codice}")
    private ResponseEntity deleteMenuByCodice(@PathVariable String codice) {
        try {
            menuService.deleteMenuByCodice(codice);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Menu", "--- OK --- Menu Eliminato Con Successo").body("Il Menu con codice: " + codice + " è stato Eliminato con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

}
