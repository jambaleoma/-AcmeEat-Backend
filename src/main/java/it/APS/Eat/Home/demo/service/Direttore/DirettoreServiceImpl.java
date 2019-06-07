package it.APS.Eat.Home.demo.service.Direttore;

import it.APS.Eat.Home.demo.Exception.IncorrectPasswordException;
import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Direttore;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.DirettoreRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.AcmehomePortale.PortaleAcmeEatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DirettoreService")
public class DirettoreServiceImpl implements DirettoreService{

    @Autowired
    private AcmeHomeService acmeHomeService;

    @Autowired
    private DirettoreRepository direttoreRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @Override
    public List<Direttore> getAllDirettori() {
        List<Direttore> consumatori = (List<Direttore>) direttoreRepository.findAll();
        if (consumatori.size() == 0) {
            throw new NotFoundException("Nessuna Consumatore Trovato");
        }
        return consumatori;
    }

    @Override
    public Direttore aggiungiDirettore(Direttore direttore) {
        Direttore d = direttoreRepository.save(direttore);
        AcmeHome acmeHome = this.acmeHomeService.getAzienda();
        List<String> codiciDirettoriRegistrati = acmeHome.getCodiciDirettori();
        codiciDirettoriRegistrati.add(d.getCodiceDirettore());
        this.acmeHomeService.aggiornaAzienda(acmeHome);
        return d;
    }

    @Override
    public Direttore getDirettoreByCodice(String codice) {
        if (this.direttoreRepository.existsById(codice)) {
            return direttoreRepository.findById(codice).orElse(null);
        } else {
            throw new NotFoundException("Il Direttore non Esiste");
        }
    }

    @Override
    public Direttore updateDirettore(Direttore nuovoDirettore, String codice) {
        if (this.direttoreRepository.existsById(codice)) {
            Direttore d = this.getDirettoreByCodice(codice);
            d.setPsw(nuovoDirettore.getPsw());
            d.setNome(nuovoDirettore.getNome());
            d.setCognome(nuovoDirettore.getCognome());
            d.setRistoranti(nuovoDirettore.getRistoranti());
            this.direttoreRepository.getCouchbaseOperations().update(d);
            return d;
        } else {
            throw new NotFoundException("Il Direttore non Esiste");
        }
    }

    @Override
    public Direttore deleteDirettoreByCodice(String codice) {
        if (this.direttoreRepository.existsById(codice)) {
            Direttore d = this.getDirettoreByCodice(codice);
            AcmeHome acmeHome = this.acmeHomeService.getAzienda();
            List<String> codiciDirettoriRegistrati = acmeHome.getCodiciDirettori();
            codiciDirettoriRegistrati.remove(d.getCodiceDirettore());
            this.acmeHomeService.aggiornaAzienda(acmeHome);
            this.direttoreRepository.delete(d);
            return d;
        } else {
            throw new NotFoundException("Il Direttore non Esiste");
        }
    }

    @Override
    public Direttore confermaInserimentoRistorante(String codiceRistorante, String codiceDirettore) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            if (this.direttoreRepository.existsById(codiceDirettore)) {
                Direttore direttore = this.direttoreRepository.findById(codiceDirettore).orElse(null);
                direttore.getRistoranti().add(ristorante);
                this.updateDirettore(direttore, direttore.getCodiceDirettore());
                return direttore;
            } else {
                throw new NotFoundException("Il Direttore non Esiste");
            }
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

    @Override
    public Direttore checkPassword(Direttore direttoreDB, String psw) {
        if(direttoreDB.getPsw().equals(psw)) {
            this.portaleAcmeEatService.setDirettoreCorrente(direttoreDB.getCodiceDirettore());
            return direttoreDB;
        }
        else
            throw new IncorrectPasswordException("Password Errata");
    }

}
