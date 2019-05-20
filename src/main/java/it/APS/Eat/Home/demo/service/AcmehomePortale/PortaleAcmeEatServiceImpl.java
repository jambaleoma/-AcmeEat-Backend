package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.repository.PortaleAcmehomeRepository;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import it.APS.Eat.Home.demo.service.Menu.MenuService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("PortaleAcmeEatService")
public class PortaleAcmeEatServiceImpl implements PortaleAcmeEatService{

    @Autowired
    private PortaleAcmehomeRepository portaleAcmehomeRepository;

    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @Autowired
    private AcmeHomeService acmeHomeService;


    @Override
    public PortaleAcmeEat creaNuovoPortale() {
        PortaleAcmeEat portaleAcmeEat = new PortaleAcmeEat();
        return this.portaleAcmehomeRepository.save(portaleAcmeEat);
    }

    @Override
    public PortaleAcmeEat getPortale() {
        return this.portaleAcmehomeRepository.findById("2b5b41d5-b25a-479a-b87a-65fb303e5781").orElse(null);
    }

    @Override
    public AcmeHome getAzienda() {
        return this.acmeHomeService.getAzienda();
    }

    @Override
    public Ristorante getRistoranteCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return portale.getRistoranteCorrente();
    }

    @Override
    public Ristorante setRistoranteCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice.equals("")) {
            portale.setRistoranteCorrente(null);
        } else {
            Ristorante r = this.ristoranteService.getRistoranteByCodice(codice);
            if (r != null) {
                portale.setRistoranteCorrente(r);
            } else {
                throw new NotFoundException("Il Ritorante non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getRistoranteCorrente();
    }

    @Override
    public Citta getCittaCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return portale.getCittaCorrente();
    }

    @Override
    public Citta setCittaCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice.equals("")) {
            portale.setCittaCorrente(null);
        } else {
            Citta c = this.cittaService.getCittaByCodice(codice);
            if (c != null) {
                portale.setCittaCorrente(c);
            } else {
                throw new NotFoundException("La Città non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCittaCorrente();
    }

    @Override
    public Direttore getDirettoreCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        return portale.getDirettoreCorrente();
    }

    @Override
    public Direttore setDirettoreCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice.equals("")) {
            portale.setDirettoreCorrente(null);
        } else {
            Direttore d = this.direttoreService.getDirettoreByCodice(codice);
            if (d != null) {
                portale.setDirettoreCorrente(d);
            } else {
                throw new NotFoundException("Il Direttore non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getDirettoreCorrente();
    }

}
