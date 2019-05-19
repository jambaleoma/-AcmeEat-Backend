package it.APS.Eat.Home.demo.service.Prodotto;

import it.APS.Eat.Home.demo.Exception.NotFoundException;
import it.APS.Eat.Home.demo.model.Prodotto;
import it.APS.Eat.Home.demo.repository.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ProdottoService")
    public class ProdottoServiceImpl implements ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Override
    public List<Prodotto> getAllProdotti() {
        List<Prodotto> prodotti = (List<Prodotto>) prodottoRepository.findAll();
        if (prodotti.size() == 0) {
            throw new NotFoundException("Nessun Prodotto Trovato");
        }
        return prodotti;
    }

    @Override
    public Prodotto aggiungiProdotto(Prodotto prodotto) {
        Prodotto p =  prodottoRepository.save(prodotto);
        p.setCodiceProdotto(p.getCodiceProdotto());
        return p;
    }

    @Override
    public Prodotto getProdottoByCodice(String codice) {
        return prodottoRepository.findById(codice).orElse(null);
    }

    @Override
    public Prodotto updateProdotto(Prodotto nuovoProdotto, String codiceProdotto) {
        if (this.prodottoRepository.existsById(codiceProdotto)) {
            Prodotto p = this.getProdottoByCodice(codiceProdotto);
            p.setNome(nuovoProdotto.getNome());
            p.setDescrizione(nuovoProdotto.getDescrizione());
            p.setPrezzo(nuovoProdotto.getPrezzo());
            this.prodottoRepository.getCouchbaseOperations().update(p);
            return p;
        } else {
            throw new NotFoundException("Il prodotto non Esiste");
        }
    }

    @Override
    public Prodotto deleteProdottoByCodice(String codice) {
        Prodotto p = this.getProdottoByCodice(codice);
        this.prodottoRepository.delete(p);
        return p;
    }
}
