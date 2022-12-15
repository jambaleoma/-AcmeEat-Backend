package it.APS.Eat.Home.demo.service.Utilities;

import it.APS.Eat.Home.demo.model.Utilities;
import it.APS.Eat.Home.demo.repository.UtilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("UtilitiesService")
public class UtilitiesServiceImpl implements UtilitiesService{

    @Autowired
    private UtilitiesRepository utilitiesRepostiory;

    @Override
    public Utilities getUtilities() {
        return this.utilitiesRepostiory.findById("utilities").orElse(null);
    }
}
