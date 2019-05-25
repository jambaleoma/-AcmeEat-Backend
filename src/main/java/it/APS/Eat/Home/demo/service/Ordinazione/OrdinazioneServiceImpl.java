package it.APS.Eat.Home.demo.service.Ordinazione;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Ordinazione;
import it.APS.Eat.Home.demo.repository.OrdinazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrdinazioneService")
public class OrdinazioneServiceImpl implements OrdinazioneService{

    @Autowired
    private OrdinazioneRepository ordinazioneRepository;

    @Override
    public List<Ordinazione> getAllOrdinazioni() {
        List<Ordinazione> ordinazioni = (List<Ordinazione>) ordinazioneRepository.findAll();
        if (ordinazioni.size() == 0) {
            throw new NotFoundException("Nessuna Ordinazione Trovata");
        }
        return ordinazioni;
    }

    @Override
    public Ordinazione aggiungiOrdinazione(Ordinazione ordinazione) {
        return ordinazioneRepository.save(ordinazione);
    }

    @Override
    public Ordinazione getOrdinazioneByCodice(String codice) {
        if (ordinazioneRepository.existsById(codice)) {
            return ordinazioneRepository.findById(codice).orElse(null);
        } else {
            throw new NotFoundException("L'Ordinazione non Esiste");
        }
    }

    @Override
    public Ordinazione updateOrdinazione(Ordinazione nuovaOrdinazione, String codice) {
        if (ordinazioneRepository.existsById(codice)) {
            Ordinazione o = this.getOrdinazioneByCodice(codice);
            o.setIndirizzo(nuovaOrdinazione.getIndirizzo());
            o.setDataOra(nuovaOrdinazione.getDataOra());
            o.setTotale(nuovaOrdinazione.getTotale());
            o.setRigheOrdinazione(nuovaOrdinazione.getRigheOrdinazione());
            o.setRistorante(nuovaOrdinazione.getRistorante());
            this.ordinazioneRepository.getCouchbaseOperations().update(o);
            return o;
        } else {
            throw new NotFoundException("L'ordinazione Non Esiste");
        }
    }

    @Override
    public Ordinazione deleteOrdinazioneByCodice(String codice) {
        if (this.ordinazioneRepository.existsById(codice)) {
            Ordinazione o = this.getOrdinazioneByCodice(codice);
            this.ordinazioneRepository.delete(o);
            return o;
        } else {
            throw new NotFoundException("L'ordinazione Non Esiste");
        }
    }
}
