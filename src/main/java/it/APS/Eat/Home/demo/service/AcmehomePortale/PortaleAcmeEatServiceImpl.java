package it.APS.Eat.Home.demo.service.AcmehomePortale;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.*;
import it.APS.Eat.Home.demo.repository.PortaleAcmeEatRepository;
import it.APS.Eat.Home.demo.service.AcmeHome.AcmeHomeService;
import it.APS.Eat.Home.demo.service.Citta.CittaService;
import it.APS.Eat.Home.demo.service.Consumatore.ConsumatoreService;
import it.APS.Eat.Home.demo.service.Direttore.DirettoreService;
import it.APS.Eat.Home.demo.service.Menu.MenuService;
import it.APS.Eat.Home.demo.service.Ordinazione.OrdinazioneService;
import it.APS.Eat.Home.demo.service.Prodotto.ProdottoService;
import it.APS.Eat.Home.demo.service.Ristorante.RistoranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component("PortaleAcmeEatService")
public class PortaleAcmeEatServiceImpl implements PortaleAcmeEatService{

    @Autowired
    private PortaleAcmeEatRepository portaleAcmehomeRepository;

    @Autowired
    private PortaleAcmeEatService portaleAcmeEatService;

    @Autowired
    private ConsumatoreService consumatoreService;

    @Autowired
    private RistoranteService ristoranteService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DirettoreService direttoreService;

    @Autowired
    private CittaService cittaService;

    @Autowired
    private OrdinazioneService ordinazioneService;

    @Autowired
    private ProdottoService prodottoService;

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
    public Menu getMenuCorrente() {
        PortaleAcmeEat portaleAcmeEat = this.getPortale();
        return this.menuService.getMenuByCodice(portaleAcmeEat.getCodiceMenuCorrente());
    }

    @Override
    public String setMenuCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceMenuCorrente(null);
        } else {
            Menu m = this.menuService.getMenuByCodice(codice);
            if (m != null) {
                portale.setCodiceMenuCorrente(m.getCodiceMenu());
            } else {
                throw new NotFoundException("Il Menù non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceMenuCorrente();
    }

    @Override
    public Consumatore getConsumatoreCorrente() {
        PortaleAcmeEat portale = this.getPortale();
        if (portale.getCodiceConsumatoreCorrente() != null)
        return this.consumatoreService.getConsumatoreByCodice(portale.getCodiceConsumatoreCorrente());
        else
            return null;
    }

    @Override
    public String setConsumatoreCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceConsumatoreCorrente(null);
        } else {
            Consumatore c = this.consumatoreService.getConsumatoreByCodice(codice);
            if (c != null) {
                portale.setCodiceConsumatoreCorrente(c.getCodiceConsumatore());
            } else {
                throw new NotFoundException("Il Consumatore non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceConsumatoreCorrente();
    }

    @Override
    public Direttore loginDirettore(Direttore direttore) {
        Direttore direttoreDB = this.direttoreService.getDirettoreByCodice(direttore.getCodiceDirettore());
        return this.direttoreService.checkPassword(direttoreDB, direttore.getPsw());
    }

    @Override
    public Consumatore loginConsumatore(Consumatore consumatore) {
        Consumatore consumatoreDB = this.consumatoreService.getConsumatoreByEmail(consumatore.getEmail());
        return this.consumatoreService.checkPassword(consumatoreDB, consumatore.getPsw());
    }

    @Override
    public List<String> getCodiciCitta() {
        PortaleAcmeEat portale = this.getPortale();
        List<Citta> listaCitta = this.cittaService.getAllCitta();
        if (listaCitta.size() > 0) {
            List<String> nomiCitta = new ArrayList<>();
            for (Citta c : listaCitta) {
                nomiCitta.add(c.getNome());
            }
            portale.setListaNomiCitta(nomiCitta);
        } else {
            throw new NotFoundException("Nessuna Citta Presente");
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getListaNomiCitta();
    }

    @Override
    public List<String> setCodiciCitta(List<String> codiciCitta) {
        PortaleAcmeEat portale = this.getPortale();
        portale.setListaNomiCitta(codiciCitta);
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getListaNomiCitta();
    }

    @Override
    public List<Ristorante> getRistorantiInCitta(String nomeCitta) {
        PortaleAcmeEat portale = this.getPortale();
        Citta c = this.cittaService.getCittaByName(nomeCitta);
        portale.setCodiceCittaCorrente(c.getCodiceCitta());
        List<Ristorante> tuttiRistoranti = this.ristoranteService.getAllRistoranti();
        List<String> listaCodiciRistorantiInCitta = new ArrayList<>();
        for (Ristorante r : tuttiRistoranti) {
            if (r.getCodiceCitta().equals(c.getCodiceCitta())) {
                listaCodiciRistorantiInCitta.add(r.getCodiceRistorante());
            }
        }
        if (listaCodiciRistorantiInCitta.size() > 0) {
            portale.setListaCodiciRistoranti(listaCodiciRistorantiInCitta);
        } else {
            throw new NotFoundException("Nessun Ristorante Presente a " + nomeCitta);
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return c.getRistoranti();
    }

    @Override
    public List<String> setRistorantiInCitta(List<String> codiciRistoranti) {
        PortaleAcmeEat portale = this.getPortale();
        portale.setListaCodiciRistoranti(codiciRistoranti);
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getListaNomiCitta();
    }

    @Override
    public Ristorante selezionaRistorante(String codiceRistorante) {
        PortaleAcmeEat portale = this.getPortale();
        Ristorante ristorante = this.ristoranteService.getRistoranteByCodice(codiceRistorante);
        Ordinazione ordinazione = new Ordinazione();
        ordinazione.setRigheOrdinazione(new ArrayList<>());
        ordinazione.setCodiceRistorante(codiceRistorante);
        ordinazione.setCodiceConsumatore(portale.getCodiceConsumatoreCorrente());
        this.ordinazioneService.aggiungiOrdinazione(ordinazione);
        this.portaleAcmeEatService.setOrdinazioneCorrente(ordinazione.getCodiceOrdinazione());
        this.portaleAcmeEatService.setRistoranteCorrente(codiceRistorante);
        this.portaleAcmeEatService.setDirettoreCorrente(ristorante.getCodiceDirettore());
        this.portaleAcmeEatService.setMenuCorrente(ristorante.getCodiceMenu());
        return ristorante;
    }

    @Override
    public Ordinazione getOrdinazioneCorrente() {
        PortaleAcmeEat portaleAcmeEat = this.getPortale();
        return this.ordinazioneService.getOrdinazioneByCodice(portaleAcmeEat.getCodiceMenuCorrente());
    }

    @Override
    public String setOrdinazioneCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceOrdinazioneCorrente(null);
        } else {
            Ordinazione o = this.ordinazioneService.getOrdinazioneByCodice(codice);
            if (o != null) {
                portale.setCodiceOrdinazioneCorrente(o.getCodiceOrdinazione());
            } else {
                throw new NotFoundException("L'Ordinazione non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceOrdinazioneCorrente();
    }

    @Override
    public Prodotto getProdottoCorrente() {
        PortaleAcmeEat portaleAcmeEat = this.getPortale();
        return this.prodottoService.getProdottoByCodice(portaleAcmeEat.getCodiceProdottoCorrente());
    }

    @Override
    public String setProdottoCorrente(String codice) {
        PortaleAcmeEat portale = this.getPortale();
        if (codice == null) {
            portale.setCodiceProdottoCorrente(null);
        } else {
            Prodotto p = this.prodottoService.getProdottoByCodice(codice);
            if (p != null) {
                portale.setCodiceProdottoCorrente(p.getCodiceProdotto());
            } else {
                throw new NotFoundException("Il Prodotto non Esiste");
            }
        }
        this.portaleAcmehomeRepository.getCouchbaseOperations().update(portale);
        return portale.getCodiceProdottoCorrente();
    }

    @Override
    public Prodotto aggiungiProdottoNellaOrdinazione(RigaOrdinazione rigaOrdinazione) {
        PortaleAcmeEat portale = this.getPortale();
        Prodotto p = this.prodottoService.getProdottoByCodice(rigaOrdinazione.getCodiceProdotto());
        this.portaleAcmeEatService.setProdottoCorrente(p.getCodiceProdotto());
        portale.setCodiceProdottoCorrente(p.getCodiceProdotto());
        Ordinazione ordinazione = this.ordinazioneService.getOrdinazioneByCodice(portale.getCodiceOrdinazioneCorrente());
        List<RigaOrdinazione> rigaOrdinazioneList = ordinazione.getRigheOrdinazione();
        RigaOrdinazione ro = this.ordinazioneService.aggiungiRigaOrdinazione(p.getCodiceProdotto(), rigaOrdinazione);
        rigaOrdinazioneList.add(ro);
        ordinazione.setRigheOrdinazione(rigaOrdinazioneList);
        this.ordinazioneService.updateOrdinazione(ordinazione, ordinazione.getCodiceOrdinazione());
        return p;
    }

    @Override
    public Ordinazione getTotaleOrdinazione() {
        PortaleAcmeEat portale = this.getPortale();
        Ordinazione ordinazioneCorrente = this.ordinazioneService.getOrdinazioneByCodice(portale.getCodiceOrdinazioneCorrente());
        Float totaleOrdinazione = 0F;
        for (RigaOrdinazione ro : ordinazioneCorrente.getRigheOrdinazione()) {
            ro.setTotaleRiga(Float.parseFloat(this.prodottoService.getProdottoByCodice(ro.getCodiceProdotto()).getPrezzo()) * ro.getQuantita());
            totaleOrdinazione += ro.getTotaleRiga();
        }
        ordinazioneCorrente.setTotale(totaleOrdinazione);
        this.ordinazioneService.updateOrdinazione(ordinazioneCorrente, ordinazioneCorrente.getCodiceOrdinazione());
        return ordinazioneCorrente;
    }

    @Override
    public Ordinazione confermaOrdinazione(Ordinazione indirizzo) {
        PortaleAcmeEat portale = this.portaleAcmeEatService.getPortale();
        Ordinazione ordinazione = this.ordinazioneService.getOrdinazioneByCodice(portale.getCodiceOrdinazioneCorrente());
        ordinazione.setIndirizzo(indirizzo.getIndirizzo());
        ordinazione.setDataOra(new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()));
        this.ordinazioneService.updateOrdinazione(ordinazione, ordinazione.getCodiceOrdinazione());
        Ristorante ristorante = this.ristoranteService.getRistoranteByCodice(portale.getCodiceRistoranteCorrente());
        Consumatore consumatore = this.consumatoreService.getConsumatoreByCodice(portale.getCodiceConsumatoreCorrente());
        List<String> listaCodiciOrdinazioniRistorante = ristorante.getCodiciOrdinazioni();
        listaCodiciOrdinazioniRistorante.add(ordinazione.getCodiceOrdinazione());
        this.ristoranteService.updateRistorante(ristorante, ristorante.getCodiceRistorante());
        List<String> listaCodiciOrdinazioniConsumatore = consumatore.getCodiciOrdinazioni();
        listaCodiciOrdinazioniConsumatore.add(ordinazione.getCodiceOrdinazione());
        this.consumatoreService.updateConsumatore(consumatore, consumatore.getCodiceConsumatore());
        return ordinazione;
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

    @Override
    public void logoutConsumatore() {
        if (this.portaleAcmeEatService.getConsumatoreCorrente() != null) {
            this.portaleAcmeEatService.setRistoranteCorrente(null);
            this.portaleAcmeEatService.setCittaCorrente(null);
            this.portaleAcmeEatService.setDirettoreCorrente(null);
            this.portaleAcmeEatService.setConsumatoreCorrente(null);
            this.portaleAcmeEatService.setCodiciCitta(null);
            this.portaleAcmeEatService.setRistorantiInCitta(null);
            this.portaleAcmeEatService.setMenuCorrente(null);
            this.portaleAcmeEatService.setOrdinazioneCorrente(null);
            this.portaleAcmeEatService.setProdottoCorrente(null);
        } else {
            throw new NotFoundException("Nessun Consumatore Loggato Sul Portale");
        }
    }

}
