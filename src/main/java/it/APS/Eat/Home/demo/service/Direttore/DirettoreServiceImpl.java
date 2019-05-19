package it.APS.Eat.Home.demo.service.Direttore;

import it.APS.Eat.Home.demo.Exception.IncorrectPasswordException;
import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.Exception.PasswordLengthException;
import it.APS.Eat.Home.demo.model.Direttore;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.DirettoreRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DirettoreService")
public class DirettoreServiceImpl implements DirettoreService{

    @Autowired
    private DirettoreRepository direttoreRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

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
        return direttoreRepository.save(direttore);
    }

    @Override
    public Direttore getDirettoreByCodice(String codice) {
        return direttoreRepository.findById(codice).orElse(null);
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
        Direttore d = this.getDirettoreByCodice(codice);
        this.direttoreRepository.delete(d);
        return d;
    }

    @Override
    public Direttore loginDirettore(Direttore direttore) {
        if (this.direttoreRepository.existsById(direttore.getCodiceDirettore())) {
            if (direttore.getPsw().length() > 5) {
                Direttore direttoreDB = this.getDirettoreByCodice(direttore.getCodiceDirettore());
                if(direttoreDB.getPsw().equals(direttore.getPsw()))
                    return direttoreDB;
                else
                    throw new IncorrectPasswordException("Password Errata");
            } else {
                throw new PasswordLengthException("Password troppo corta, inserire almeno 6 caratteri");
            }
        } else {
            throw new NotFoundException("Il Direttore non Esiste");
        }
    }

    @Override
    public Direttore confermaInserimentoRistorante(String codiceRistorante, Direttore direttoreCorrente) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            if (this.direttoreRepository.existsById(direttoreCorrente.getCodiceDirettore())) {
                Direttore direttore = this.direttoreRepository.findById(direttoreCorrente.getCodiceDirettore()).orElse(null);
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
}
