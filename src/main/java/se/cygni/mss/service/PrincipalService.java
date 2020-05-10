package se.cygni.mss.service;

import org.springframework.data.repository.CrudRepository;
import se.cygni.mss.tsv.model.Principal;

public interface PrincipalService extends CrudRepository<Principal, Integer> {

    Iterable<Principal> findByTconst(String tconst);
    Iterable<Principal> findByNconst(String nconst);
    Iterable<Principal> findByTconstAndNconst(String tconst, String nconst);
}
