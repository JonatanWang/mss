package se.cygni.mss.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.cygni.mss.tsv.model.Rating;

public interface RatingService {

    Iterable<Rating> findAll();

    Page<Rating> findByTconst(String tconst, Pageable pageable);

    // Page<Rating> findByTconstUsingCustomQuery(String tconst, Pageable pageable);

    // Page<Rating> findByFilteredAverageRatingQuery(String averageRating, Pageable pageable);

    // Page<Rating> findByTconstAndFilteredAverageRatingQuery(String tconst, String averageRating, Pageable pageable);

    long count();

    void delete(Rating rating);

}
