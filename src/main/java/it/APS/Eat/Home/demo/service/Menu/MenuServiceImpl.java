package it.APS.Eat.Home.demo.service.Menu;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Menu;
import it.APS.Eat.Home.demo.model.Prodotto;
import it.APS.Eat.Home.demo.repository.MenuRepository;
import it.APS.Eat.Home.demo.repository.ProdottoRepository;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("MenuService")
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private ProdottoServiceImpl prodottoService;

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> menu = (List<Menu>) menuRepository.findAll();
        if (menu.size() == 0) {
            throw new NotFoundException("Nessun Menu Trovato");
        }
        return menu;
    }

    @Override
    public Menu aggiungiMenu(Menu menu) {
        menu.setCodiciProdotti(new ArrayList<>());
        menu.setIsListaProdottiCompleta(false);
        return menuRepository.save(menu);
    }

    @Override
    public Menu aggiungiProdottoNelMenu(String codiceMenu, Prodotto prodottoDaInserire) {
        if (this.menuRepository.existsById(codiceMenu)) {
            Prodotto prodottoSalvato = this.prodottoService.aggiungiProdotto(prodottoDaInserire);
            if (this.prodottoRepository.existsById(prodottoSalvato.getCodiceProdotto())) {
                Menu menu = this.getMenuByCodice(codiceMenu);
                List<String> listaCodiciProdotti;
                if (menu.getCodiciProdotti() != null) {
                    listaCodiciProdotti = new ArrayList<>(menu.getCodiciProdotti());
                } else {
                    listaCodiciProdotti = new ArrayList<>();
                }
                listaCodiciProdotti.add(prodottoSalvato.getCodiceProdotto());
                menu.setCodiciProdotti(listaCodiciProdotti);
                this.updateMenu(menu, codiceMenu);
                return menu;
            } else {
                throw new NotFoundException("Il Prodotto non Esiste");
            }
        } else {
            throw new NotFoundException("Il Menu non Esiste");
        }
    }

    @Override
    public Menu selezionaProdottoSpecialeDelMenu(String codiceMenu, String codiceProdottoSpeciale) {
        if (this.prodottoRepository.existsById(codiceProdottoSpeciale)) {
            if (this.menuRepository.existsById(codiceMenu)) {
                Menu menu = this.getMenuByCodice(codiceMenu);
                menu.setSpecialita(codiceProdottoSpeciale);
                this.updateMenu(menu, codiceMenu);
                return menu;
            } else {
                throw new NotFoundException("Il Menu non Esiste");
            }
        } else {
            throw new NotFoundException("Il Prodotto non Esiste");
        }
    }

    @Override
    public Menu eliminaProdottoNelMenu(String codiceMenu, String codiceProdottoDaEliminare) {
        if (this.prodottoRepository.existsById(codiceProdottoDaEliminare)) {
            if (this.menuRepository.existsById(codiceMenu)) {
                Menu menu = this.getMenuByCodice(codiceMenu);
                List<String> listaCodiciProdotti;
                if (menu.getCodiciProdotti() != null) {
                    listaCodiciProdotti = new ArrayList<>(menu.getCodiciProdotti());
                } else {
                    listaCodiciProdotti = new ArrayList<>();
                }
                listaCodiciProdotti.remove(codiceProdottoDaEliminare);
                menu.setCodiciProdotti(listaCodiciProdotti);
                this.updateMenu(menu, codiceMenu);
                return menu;
            } else {
                throw new NotFoundException("Il Menu non Esiste");
            }
        } else {
            throw new NotFoundException("Il Prodotto non Esiste");
        }
    }

    @Override
    public Menu getMenuByCodice(String codice) {
        return menuRepository.findById(codice).orElse(null);
    }

    @Override
    public Menu updateMenu(Menu nuovoMenu, String codice) {
        if (this.menuRepository.existsById(codice)) {
            Menu m = this.getMenuByCodice(codice);
            m.setCodiciProdotti(nuovoMenu.getCodiciProdotti());
            m.setSpecialita(nuovoMenu.getSpecialita());
            m.setIsListaProdottiCompleta(nuovoMenu.getIsListaProdottiCompleta());
            this.menuRepository.getCouchbaseOperations().update(m);
            return m;
        } else {
            throw new NotFoundException("Il Menu non Esiste");
        }
    }

    @Override
    public Menu deleteMenuByCodice(String codice) {
        Menu m = this.getMenuByCodice(codice);
        this.menuRepository.delete(m);
        return m;
    }

    @Override
    public Menu terminaInserimento(String codice) {
        if (this.menuRepository.existsById(codice)) {
            Menu m = this.getMenuByCodice(codice);
            m.setIsListaProdottiCompleta(true);
            this.updateMenu(m, codice);
            return m;
        } else {
            throw new NotFoundException("Il Menu non Esiste");
        }
    }
}
