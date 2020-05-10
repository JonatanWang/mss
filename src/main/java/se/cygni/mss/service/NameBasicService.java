package se.cygni.mss.service;

import org.springframework.data.repository.CrudRepository;
import se.cygni.mss.tsv.model.NameBasic;

public interface NameBasicService extends CrudRepository<NameBasic, Integer>{

        Iterable<NameBasic> findByNconst(String nconst);
        Iterable<NameBasic> findByPrimaryName(String primaryName);
        Iterable<NameBasic> findByNconstAndPrimaryName(String nconst, String primaryName);
}
