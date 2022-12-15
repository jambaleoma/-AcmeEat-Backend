package it.APS.Eat.Home.demo.service.Citta;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.model.Utilities;
import it.APS.Eat.Home.demo.repository.CittaRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Utilities.UtilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("CittaService")
public class CittaServiceImpl implements CittaService {

    @Autowired
    private AcmeHomeService acmeHomeService;

    @Autowired
    private CittaRepository cittaRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Autowired
    private UtilitiesService utilitiesService;

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
        Citta c = cittaRepository.save(citta);
        AcmeHome acmeHome = this.acmeHomeService.getAzienda();
        List<String> codiciCittaRegistrate = acmeHome.getCodiciCitta();
        codiciCittaRegistrate.add(c.getCodiceCitta());
        this.acmeHomeService.aggiornaAzienda(acmeHome);
        return c;
    }

    @Override
    public Citta getCittaByCodice(String codice) {
        if (this.cittaRepository.existsById(codice)) {
            return cittaRepository.findById(codice).orElse(null);
        } else {
            Utilities utilities = this.utilitiesService.getUtilities();
            for (Citta c : utilities.getListacitta()) {
                if (c.getCodice().equals(codice)) {
                    return c;
                }
            }
        }
        throw new NotFoundException("La Citta non Esiste");
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
            AcmeHome acmeHome = this.acmeHomeService.getAzienda();
            List<String> codiciCittaRegistrate = acmeHome.getCodiciCitta();
            codiciCittaRegistrate.remove(c.getCodiceCitta());
            this.acmeHomeService.aggiornaAzienda(acmeHome);
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
                c.getRistoranti().add(ristorante.getCodiceRistorante());
                this.updateCitta(c, c.getCodiceCitta());
                return c;
            } else {
                Utilities utilities = this.utilitiesService.getUtilities();
                for (Citta c : utilities.getListacitta()) {
                    if (c.getCodice().equals(ristorante.getCodiceCitta())) {
                        return this.setNewCityFromList(c);
                    }
                }
            }
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
        throw new NotFoundException("La Citta non Esiste");
    }

    @Override
    public Citta disattivaRistorante(String codiceRistorante) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            if (this.cittaRepository.existsById(ristorante.getCodiceCitta())) {
                Citta c = this.cittaRepository.findById(ristorante.getCodiceCitta()).orElse(null);
                c.getRistoranti().remove(ristorante.getCodiceRistorante());
                this.updateCitta(c, c.getCodiceCitta());
                return c;
            } else {
                throw new NotFoundException("La Citta non Esiste");
            }
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

    private Citta setNewCityFromList(Citta c) {
        Citta newCity = new Citta();
        newCity.setNome(c.getNome());
        newCity.setCodiceCitta(c.getCodice());
        Set<String> listaCodiciRistorantiInCitta = new HashSet<>();
        newCity.setRistoranti(listaCodiciRistorantiInCitta);
        AcmeHome acmeHome = this.acmeHomeService.getAzienda();
        List<String> codiciCittaRegistrate = acmeHome.getCodiciCitta();
        codiciCittaRegistrate.add(c.getCodice());
        this.acmeHomeService.aggiornaAzienda(acmeHome);
        return this.cittaRepository.save(newCity);
    }

}
