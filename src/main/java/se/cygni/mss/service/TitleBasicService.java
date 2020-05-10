package se.cygni.mss.service;

import org.springframework.data.repository.CrudRepository;
import se.cygni.mss.tsv.model.TitleBasic;


public interface TitleBasicService extends CrudRepository<TitleBasic, Integer> {

    Iterable<TitleBasic> findByTconst(String tconst);
    Iterable<TitleBasic> findByPrimaryTitle(String primaryTitle);
    Iterable<TitleBasic> findByOriginalTitle(String originalTitle);
    Iterable<TitleBasic> findByTconstAndPrimaryTitleAndOriginalTitle(String tconst, String primaryTitle, String originalTitle);
}
