package it.APS.Eat.Home.demo.repository;

import it.APS.Eat.Home.demo.model.Ordinazione;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "ordinazione")
public interface OrdinazioneRepository extends CouchbasePagingAndSortingRepository<Ordinazione, String>, CrudRepository<Ordinazione, String> {}