package it.APS.Eat.Home.demo.service.Ordinazione;

import it.APS.Eat.Home.demo.model.Ordinazione;
import it.APS.Eat.Home.demo.model.Prodotto;
import it.APS.Eat.Home.demo.model.RigaOrdinazione;

import java.util.List;

public interface OrdinazioneService {

    List<Ordinazione> getAllOrdinazioni();
    Ordinazione aggiungiOrdinazione(Ordinazione ordinazione);
    Ordinazione getOrdinazioneByCodice(String codice);
    Ordinazione updateOrdinazione(Ordinazione nuovaOrdinazione, String codice);
    Ordinazione deleteOrdinazioneByCodice(String codice);
    RigaOrdinazione aggiungiRigaOrdinazione(String codiceProdotto, RigaOrdinazione rigaOrdinazione);
}
