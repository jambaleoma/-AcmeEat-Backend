package it.APS.Eat.Home.demo.service.Prodotto;

import it.APS.Eat.Home.demo.model.Prodotto;

import java.util.List;

public interface ProdottoService {

    List<Prodotto> getAllProdotti();
    Prodotto aggiungiProdotto(Prodotto prodotto);
    Prodotto getProdottoByCodice(String codice);
    Prodotto updateProdotto(Prodotto nuovoProdotto, String codice);
    Prodotto deleteProdottoByCodice(String codice);
}
