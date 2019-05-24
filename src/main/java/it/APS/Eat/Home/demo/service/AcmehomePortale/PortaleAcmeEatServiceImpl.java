package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.Exception.IncorrectPasswordException;
import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.Exception.PasswordLengthException;
import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.repository.AcmeHomeRepository;
import it.APS.Eat.Home.demo.repository.DirettoreRepository;
import it.APS.Eat.Home.demo.repository.PortaleAcmeEatRepository;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("PortaleAcmeEatService")
public class PortaleAcmeEatServiceImpl implements PortaleAcmeEatService{

    @Autowired
    private PortaleAcmeEatRepository portaleAcmehomeRepository;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @Autowired
    private DirettoreRepository direttoreRepository;

    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @Autowired
    private AcmeHomeService acmeHomeService;


    @Override
    public PortaleAcmeEat creaNuovoPortale() {
        PortaleAcmeEat portaleAcmeEat = new PortaleAcmeEat();
        portaleAcmeEat.setCodiceAzienda(this.acmeHomeService.getAzienda().getCodiceAzienda());
        return this.portaleAcmehomeRepository.save(portaleAcmeEat);
    }

    @Override
    public PortaleAcmeEat getPortale() {
        List<PortaleAcmeEat> listaPortali = (List<PortaleAcmeEat>) this.portaleAcmehomeRepository.findAll();
        return listaPortali.get(0);
    }

    @Override
    public AcmeHome getAzienda() {
        return this.acmeHomeService.getAzienda();
    }

    @Override
    public Ristorante getRistoranteCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return this.ristoranteService.getRistoranteByCodice(portale.getCodiceRistoranteCorrente());
    }

    @Override
    public String setRistoranteCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceRistoranteCorrente(null);
        } else {
            Ristorante r = this.ristoranteService.getRistoranteByCodice(codice);
            if (r != null) {
                portale.setCodiceRistoranteCorrente(r.getCodiceRistorante());
            } else {
                throw new NotFoundException("Il Ritorante non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceRistoranteCorrente();
    }

    @Override
    public Citta getCittaCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return this.cittaService.getCittaByCodice(portale.getCodiceCittaCorrente());
    }

    @Override
    public String setCittaCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceCittaCorrente(null);
        } else {
            Citta c = this.cittaService.getCittaByCodice(codice);
            if (c != null) {
                portale.setCodiceCittaCorrente(c.getCodiceCitta());
            } else {
                throw new NotFoundException("La Città non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceRistoranteCorrente();
    }

    @Override
    public Direttore getDirettoreCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return this.direttoreService.getDirettoreByCodice(portale.getCodiceDirettoreCorrente());
    }

    @Override
    public String setDirettoreCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceDirettoreCorrente(null);
        } else {
            Direttore d = this.direttoreService.getDirettoreByCodice(codice);
            if (d != null) {
                portale.setCodiceDirettoreCorrente(d.getCodiceDirettore());
            } else {
                throw new NotFoundException("Il Direttore non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceDirettoreCorrente();
    }

    @Override
    public Direttore loginDirettore(Direttore direttore) {
        Direttore direttoreDB = this.direttoreService.getDirettoreByCodice(direttore.getCodiceDirettore());
        return this.direttoreService.checkPassword(direttoreDB, direttore.getPsw());
    }

    @Override
    public void logoutDirettore() {
        if (this.portaleAcmeEatService.getDirettoreCorrente() != null) {
            this.portaleAcmeEatService.setRistoranteCorrente(null);
            this.portaleAcmeEatService.setCittaCorrente(null);
            this.portaleAcmeEatService.setDirettoreCorrente(null);
        } else {
            throw new NotFoundException("Nessun Direttore Loggato Sul Portale");
        }
    }

}
