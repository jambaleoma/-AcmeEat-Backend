package it.APS.Eat.Home.demo.service.AcmeHome;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Citta;
import it.APS.Eat.Home.demo.model.Direttore;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.AcmeHomeRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("AcmeHomeService")
public class AcmeHomeServiceImpl implements AcmeHomeService{

    @Autowired
    private AcmeHomeRepository acmeHomeRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Override
    public AcmeHome getAzienda() {
        List<AcmeHome> aziende = (List<AcmeHome>) this.acmeHomeRepository.findAll();
        return aziende.get(0);
    }

    @Override
    public AcmeHome aggingiAzienda(AcmeHome acmeHome) { return acmeHomeRepository.save(acmeHome);}

    @Override
    public List<String> getUtlimiQuindiciRistorantiInseriti() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getCodiciQuindiciRistorantiRecenti();
    }

    @Override
    public List<Citta> getAllCitta() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getCitta();
    }

    @Override
    public List<Direttore> getAllDirettori() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getDirettori();
    }

    @Override
    public AcmeHome addRistoranteToUltimi15(String codiceRistorante) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            AcmeHome acmeHome = this.getAzienda();
            acmeHome.getCodiciQuindiciRistorantiRecenti().add(0, ristorante.getCodiceRistorante());
            if(acmeHome.getCodiciQuindiciRistorantiRecenti().size() > 15)
                acmeHome.getCodiciQuindiciRistorantiRecenti().remove(acmeHome.getCodiciQuindiciRistorantiRecenti().size() - 1);
            this.acmeHomeRepository.getCouchbaseOperations().update(acmeHome);
            return acmeHome;
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }


}
