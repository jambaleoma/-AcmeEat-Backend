package it.APS.Eat.Home.demo.repository;

import it.APS.Eat.Home.demo.model.PortaleAcmeEat;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "ristorante")
public interface PortaleAcmehomeRepository extends CouchbasePagingAndSortingRepository<PortaleAcmeEat, String>, CrudRepository<PortaleAcmeEat, String> {}