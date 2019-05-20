package it.APS.Eat.Home.demo.service.AcmeHome;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.AcmeHomeRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import it.APS.Eat.Home.demo.service.Citta.CittaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AcmeHomeService")
public class AcmeHomeServiceImpl implements AcmeHomeService{

    @Autowired
    private AcmeHomeRepository acmeHomeRepository;

    @Autowired
    private CittaServiceImpl cittaService;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Override
    public AcmeHome getAzienda() {
        return acmeHomeRepository.findById("eb9eab79-fed0-4096-97a2-9c9021a29fe6").orElse(null);
    }

    @Override
    public AcmeHome aggingiAzienda(AcmeHome acmeHome) { return acmeHomeRepository.save(acmeHome);}

    @Override
    public List<Ristorante> getUtlimiQuindiciRistorantiInseriti() {
        AcmeHome azienda = this.getAzienda();
        return  azienda.getQuindiciRistorantiRecenti();
    }

    @Override
    public List<Citta> getAllCitta() {
        List<Citta> citta = this.cittaService.getAllCitta();
        return citta;
    }

    @Override
    public AcmeHome confermaInserimentoRistorante(String codiceRistorante) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            AcmeHome acmeHome = this.getAzienda();
            acmeHome.getQuindiciRistorantiRecenti().add(0, ristorante);
            if(acmeHome.getQuindiciRistorantiRecenti().size() > 15)
                acmeHome.getQuindiciRistorantiRecenti().remove(acmeHome.getQuindiciRistorantiRecenti().size() - 1);
            this.acmeHomeRepository.getCouchbaseOperations().update(acmeHome);
            return acmeHome;
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }


}
