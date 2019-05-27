package it.APS.Eat.Home.demo.service.Consumatore;

import it.APS.Eat.Home.demo.Exception.IncorrectPasswordException;
import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Consumatore;
import it.APS.Eat.Home.demo.repository.ConsumatoreRepository;
import it.APS.Eat.Home.demo.service.AcmehomePortale.PortaleAcmeEatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ConsumatoreService")
public class ConsumatoreServiceImpl implements ConsumatoreService {

    @Autowired
    private ConsumatoreRepository consumatoreRepository;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @Override
    public List<Consumatore> getAllConsumatori() {
        List<Consumatore> consumatori = (List<Consumatore>) consumatoreRepository.findAll();
        if (consumatori.size() == 0) {
            throw new NotFoundException("Nessuna Consumatore Trovato");
        }
        return consumatori;
    }

    @Override
    public Consumatore aggiungiConsumatore(Consumatore consumatore) {
        return consumatoreRepository.save(consumatore);
    }

    @Override
    public Consumatore getConsumatoreByCodice(String codice) {
        return consumatoreRepository.findById(codice).orElse(null);
    }

    @Override
    public Consumatore getConsumatoreByEmail(String email) {
        Consumatore consumatore = null;
        List<Consumatore> consumatori = this.getAllConsumatori();
        for (Consumatore c : consumatori) {
            if (c.getEmail().equals(email)) {
                consumatore = c;
            }
        }
        return consumatore;
    }

    @Override
    public Consumatore updateConsumatore(Consumatore nuovoConsumatore, String codice) {
       if (this.consumatoreRepository.existsById(codice)) {
           Consumatore c = this.getConsumatoreByCodice(codice);
           c.setEmail(nuovoConsumatore.getEmail());
           c.setPsw(nuovoConsumatore.getPsw());
           c.setNome(nuovoConsumatore.getNome());
           c.setCognome(nuovoConsumatore.getCognome());
           c.setCellulare(nuovoConsumatore.getCellulare());
           c.setCodiciOrdinazioni(nuovoConsumatore.getCodiciOrdinazioni());
           this.consumatoreRepository.getCouchbaseOperations().update(c);
           return c;
       } else {
           throw new NotFoundException("Il consumatore non Esiste");
       }
    }

    @Override
    public Consumatore deleteConsumatoreByCodice(String codice) {
        Consumatore c = this.getConsumatoreByCodice(codice);
        this.consumatoreRepository.delete(c);
        return c;
    }

    @Override
    public Consumatore checkPassword(Consumatore consumatoreDB, String psw) {
        if(consumatoreDB.getPsw().equals(psw)) {
            this.portaleAcmeEatService.setConsumatoreCorrente(consumatoreDB.getCodiceConsumatore());
            return consumatoreDB;
        }
        else
            throw new IncorrectPasswordException("Password Errata");
    }
}
