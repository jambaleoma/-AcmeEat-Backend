package it.APS.Eat.Home.demo.service.Citta;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.repository.CittaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CittaService")
public class CittaServiceImpl implements CittaService {

    @Autowired
    private CittaRepository cittaRepository;

    @Override
    public List<Citta> getAllCitta() {
        List<Citta> citta = (List<Citta>) cittaRepository.findAll();
        if (citta.size() == 0) {
            throw new NotFoundException("Nessuna Citta Trovata");
        }
        return citta;
    }

    @Override
    public Citta aggiungiCitta(Citta citta) {
        return cittaRepository.save(citta);
    }

    @Override
    public Citta getCittaByCodice(String codice) {
        return cittaRepository.findById(codice).orElse(null);
    }

    @Override
    public Citta updateCitta(Citta nuovaCitta, String codice) {
        if (this.cittaRepository.existsById(codice)) {
            Citta c = this.getCittaByCodice(codice);
            c.setNome(nuovaCitta.getNome());
            c.setRistoranti(nuovaCitta.getRistoranti());
            this.cittaRepository.getCouchbaseOperations().update(c);
            return c;
        } else {
            throw new NotFoundException("La Citta non Esiste");
        }
    }

    @Override
    public Citta deleteCittaByCodice(String codice) {
        Citta c = this.getCittaByCodice(codice);
        this.cittaRepository.delete(c);
        return c;
    }

}
