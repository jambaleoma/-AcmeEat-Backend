package it.APS.Eat.Home.demo.service.Consumatore;

import it.APS.Eat.Home.demo.model.Consumatore;

import java.util.List;

public interface ConsumatoreService {

    List<Consumatore> getAllConsumatori();
    Consumatore aggiungiConsumatore(Consumatore consumatore);
    Consumatore getConsumatoreByCodice(String codice);
    Consumatore getConsumatoreByEmail(String email);
    Consumatore updateConsumatore(Consumatore nuovoConsumatore, String codice);
    Consumatore deleteConsumatoreByCodice(String codice);
    Consumatore checkPassword(Consumatore consumatore, String psw);

}
