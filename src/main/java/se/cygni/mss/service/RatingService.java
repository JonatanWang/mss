package se.cygni.mss.service;

import org.springframework.data.repository.CrudRepository;
import se.cygni.mss.tsv.model.Rating;

public interface RatingService extends CrudRepository<Rating, Integer> {

    Iterable<Rating> findByTconst(String tconst);
    Iterable<Rating> findByAverageRating(String averageRating);
    Iterable<Rating> findByTconstAndAverageRating(String tconst, String averageRating);
}
