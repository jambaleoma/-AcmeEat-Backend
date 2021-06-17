package it.APS.Eat.Home.demo.service.AcmeHome;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.AcmeHome;
import it.APS.Eat.Home.demo.model.Ristorante;
import it.APS.Eat.Home.demo.repository.AcmeHomeRepository;
import it.APS.Eat.Home.demo.repository.RistoranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Component("AcmeHomeService")
public class AcmeHomeServiceImpl implements AcmeHomeService{

    @Autowired
    private AcmeHomeRepository acmeHomeRepository;

    @Autowired
    private RistoranteRepository ristoranteRepository;

    @Override
    @NotNull
    public AcmeHome getAzienda() {
        List<AcmeHome> aziende = (List<AcmeHome>) this.acmeHomeRepository.findAll();
        if (aziende == null) {
            aziende = new ArrayList<>();
         }
        return aziende.get(0);
    }

    @Override
    public AcmeHome aggiungiAzienda(AcmeHome acmeHome) { return acmeHomeRepository.save(acmeHome);}

    @Override
    public List<String> getAllCitta() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getCodiciCitta();
    }

    @Override
    public List<String> getAllDirettori() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getCodiciDirettori();
    }

    @Override
    @NotNull public AcmeHome confermaInserimentoRistorante(String codiceRistorante) {
        if (this.ristoranteRepository.existsById(codiceRistorante)) {
            Ristorante ristorante = ristoranteRepository.findById(codiceRistorante).orElse(null);
            AcmeHome acmeHome = this.getAzienda();
            if (acmeHome.getCodiciRistoranti() == null) {
                List<String> listaCodiciRistorante = new ArrayList<>();
                listaCodiciRistorante.add(ristorante.getCodiceRistorante());
                acmeHome.setCodiciRistoranti(listaCodiciRistorante);
            } else {
                acmeHome.getCodiciRistoranti().add(ristorante.getCodiceRistorante());
            }
            acmeHome.getCodiciQuindiciRistorantiRecenti().add(0, ristorante.getCodiceRistorante());
            if(acmeHome.getCodiciQuindiciRistorantiRecenti().size() > 15)
                acmeHome.getCodiciQuindiciRistorantiRecenti().remove(acmeHome.getCodiciQuindiciRistorantiRecenti().size() - 1);
            this.acmeHomeRepository.getCouchbaseOperations().update(acmeHome);
            return acmeHome;
        } else {
            throw new NotFoundException("Il Ristorante non Esiste");
        }
    }

    @Override
    public List<String> getUtlimiQuindiciRistorantiInseriti() {
        AcmeHome azienda = this.getAzienda();
        return azienda.getCodiciQuindiciRistorantiRecenti();
    }

    @Override
    public AcmeHome aggiornaAzienda(AcmeHome acmeHome) {
            AcmeHome ah = this.getAzienda();
            ah.setCodiciCitta(acmeHome.getCodiciCitta());
            ah.setCodiciDirettori(acmeHome.getCodiciDirettori());
            ah.setCodiciConsumatori(acmeHome.getCodiciConsumatori());
            ah.setCodiciRistoranti(acmeHome.getCodiciRistoranti());
            ah.setCodiciQuindiciRistorantiRecenti(acmeHome.getCodiciQuindiciRistorantiRecenti());
            this.acmeHomeRepository.getCouchbaseOperations().update(ah);
            return ah;
    }

}
