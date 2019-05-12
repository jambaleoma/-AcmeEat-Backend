package it.APS.Eat.Home.demo.repository;

import it.APS.Eat.Home.demo.model.Citta;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "citta")
public interface CittaRepository extends CouchbasePagingAndSortingRepository<Citta, String>, CrudRepository<Citta, String> {}
