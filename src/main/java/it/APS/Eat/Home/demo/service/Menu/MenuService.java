package it.APS.Eat.Home.demo.service.Menu;

import it.APS.Eat.Home.demo.model.Menu;
import it.APS.Eat.Home.demo.model.Prodotto;

import java.util.ArrayList;
import java.util.List;

public interface MenuService {

    List<Menu> getAllMenu();
    Menu aggiungiMenu(Menu menu);
    Menu aggiungiProdottoNelMenu(Prodotto prodottoDaInserire);
    Menu selezionaProdottoSpecialeDelMenu(String codiceMenu, String codiceProdottoSpeciale);
    Menu eliminaProdottoNelMenu(String codiceMenu, String codiceProdottoDaEliminare);
    Menu getMenuByCodice(String codice);
    ArrayList<Prodotto> getAllProductsByMenu(String codice);
    Menu updateMenu(Menu nuovoMenu, String codice);
    Menu deleteMenuByCodice(String codice);
}
