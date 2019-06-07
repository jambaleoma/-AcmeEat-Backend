package it.APS.Eat.Home.demo.service.Ristorante;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Menu;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import it.APS.Eat.Home.demo.service.AcmehomePortale.PortaleAcmeEatService;
import it.APS.Eat.Home.demo.service.Menu.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("RistoranteService")
public class RistoranteServiceImpl implements RistoranteService{

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Autowired
    private MenuServiceImpl menuService;

    @Override
    public List<Ristorante> getAllRistoranti() {
        List<Ristorante> ristoranti = (List<Ristorante>) ristoranteRepository.findAll();
        if (ristoranti.size() == 0) {
            throw new NotFoundException("Nessun Ristorante Trovato");
        }
        return ristoranti;
    }

    @Override
    public Ristorante aggiungiRistorante(Ristorante ristorante) {
        Menu m = new Menu();
        this.menuService.aggiungiMenu(m);
        ristorante.setCodiceDirettore(this.portaleAcmeEatService.getDirettoreCorrente().getCodiceDirettore());
        ristorante.setCodiciOrdinazioni(new ArrayList<>());
        ristorante.setCodiceMenu(m.getCodiceMenu());
        Ristorante r = this.ristoranteRepository.save(ristorante);
        this.portaleAcmeEatService.setRistoranteCorrente(r.getCodiceRistorante());
        this.portaleAcmeEatService.setMenuCorrente(m.getCodiceMenu());
        this.portaleAcmeEatService.setCittaCorrente(ristorante.getCodiceCitta());
        return r;
    }

    @Override
    public Ristorante getRistoranteByCodice(String codice) {
        return ristoranteRepository.findById(codice).orElse(null);
    }

    @Override
    public Ristorante updateRistorante(Ristorante nuovoRistorante, String codice) {
        if (this.ristoranteRepository.existsById(codice)) {
            Ristorante r = this.getRistoranteByCodice(codice);
            r.setCodiceRistorante(nuovoRistorante.getCodiceRistorante());
            r.setNome(nuovoRistorante.getNome());
            r.setDescrizione(nuovoRistorante.getDescrizione());
            r.setCodiceCitta(nuovoRistorante.getCodiceCitta());
            r.setCodiceMenu(nuovoRistorante.getCodiceMenu());
            r.setCodiceDirettore(nuovoRistorante.getCodiceDirettore());
            r.setCodiciOrdinazioni(nuovoRistorante.getCodiciOrdinazioni());
            this.ristoranteRepository.getCouchbaseOperations().update(r);
            return r;
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

    @Override
    public Ristorante deleteRistoranteByCodice(String codice) {
        Ristorante r = this.getRistoranteByCodice(codice);
        this.ristoranteRepository.delete(r);
        return r;
    }
}
