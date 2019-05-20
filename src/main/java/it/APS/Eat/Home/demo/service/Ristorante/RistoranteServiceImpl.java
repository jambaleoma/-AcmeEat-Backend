package it.APS.Eat.Home.demo.service.Ristorante;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Menu;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.CittaRepository;
import it.APS.Eat.Home.demo.repository.MenuRepository;
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
    private MenuRepository menuRepository;

    @Autowired
    private MenuServiceImpl menuService;

    @Autowired
    private CittaRepository cittaRepository;

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
        ristorante.setCodiciOridinazioni(new ArrayList<>());
        ristorante.setCodiceMenu(m.getCodiceMenu());
        Ristorante r = this.ristoranteRepository.save(ristorante);
        this.portaleAcmeEatService.setRistoranteCorrente(r.getCodiceRistorante());
        this.portaleAcmeEatService.setCittaCorrente(ristorante.getCodiceCitta());
        return r;
    }

    @Override
    public Ristorante selezionaCittaDelRistorante(String codiceRistorante, String codiceCitta) {
        if (this.menuRepository.existsById(codiceCitta)) {
            if (this.ristoranteRepository.existsById(codiceRistorante)) {
                Ristorante ristorante = this.getRistoranteByCodice(codiceRistorante);
                ristorante.setCodiceCitta(codiceCitta);
                this.updateRistorante(ristorante, codiceRistorante);
                return ristorante;
            } else {
                throw new NotFoundException("Il Ristorante non Esiste");
            }
        } else {
            throw new NotFoundException("La citta non Esiste");
        }
    }

    @Override
    public Ristorante aggiungiDirettoreDelRistorante(String codiceRistorante, String codiceDirettore) {
        if (this.menuRepository.existsById(codiceDirettore)) {
            if (this.ristoranteRepository.existsById(codiceRistorante)) {
                Ristorante ristorante = this.getRistoranteByCodice(codiceRistorante);
                ristorante.setCodiceDirettore(codiceDirettore);
                this.updateRistorante(ristorante, codiceRistorante);
                return ristorante;
            } else {
                throw new NotFoundException("Il Ristorante non Esiste");
            }
        } else {
            throw new NotFoundException("Il Direttore non Esiste");
        }
    }

//    @Override
//    public Ristorante aggiungiCucinaDelRistorante(String codiceRistorante, String codiceCucina) {
//        //TODO: Creare repository per Cucina:
//        return null;
//    }

//    @Override
//    public Ristorante aggiungiOrdinazioneDelRistorante(String codiceRistorante, String codiceOrdinazione) {
//        //TODO: Creare repository per Ordinazione:
//        return null;
//    }

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
            r.setCodiciOridinazioni(nuovoRistorante.getCodiciOridinazioni());
            this.ristoranteRepository.getCouchbaseOperations().update(r);
            return r;
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

    @Override
    public Ristorante deleteRistoranteByCodice(String codice) {
        Ristorante r = this.getRistoranteByCodice(codice);
        //TODO: Logica eliminazione ristorante in posizione < 15:
//        Integer posizioneRistorante = r.getPosizione();
//        for (Ristorante ristorante : this.getAllRistoranti()) {
//            if (ristorante.getPosizione() > posizioneRistorante) {
//
//            }
//        }
        this.ristoranteRepository.delete(r);
        return r;
    }
}
