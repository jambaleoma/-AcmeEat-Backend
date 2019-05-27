package it.APS.Eat.Home.demo.service.Citta;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.CittaRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("CittaService")
public class CittaServiceImpl implements CittaService {

    @Autowired
    private CittaRepository cittaRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

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
        if (this.cittaRepository.existsById(codice)) {
            return cittaRepository.findById(codice).orElse(null);
        } else {
            throw new NotFoundException("La Citta non Esiste");
        }
    }

    @Override
    public Citta getCittaByName(String nomeCitta) {
        List<Citta> citta = this.getAllCitta();
        Citta cittaDB = null;
        for (Citta c : citta) {
         if (c.getNome().equals(nomeCitta))
             cittaDB = c;
        }
        return cittaDB;
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
        if (this.cittaRepository.existsById(codice)) {
            Citta c = this.getCittaByCodice(codice);
            this.cittaRepository.delete(c);
            return c;
        } else {
            throw new NotFoundException("La Citta non Esiste");
        }
    }

    @Override
    public Citta confermaInserimentoRistorante(String codiceRistorante) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            if (this.cittaRepository.existsById(ristorante.getCodiceCitta())) {
                Citta c = this.cittaRepository.findById(ristorante.getCodiceCitta()).orElse(null);
                c.getRistoranti().add(ristorante);
                this.updateCitta(c, c.getCodiceCitta());
                return c;
            } else {
                throw new NotFoundException("La Citta non Esiste");
            }
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

}
