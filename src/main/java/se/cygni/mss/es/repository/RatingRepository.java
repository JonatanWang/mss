package se.cygni.mss.es.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import se.cygni.mss.tsv.model.Rating;
import java.math.BigInteger;

@Repository
public interface RatingRepository extends ElasticsearchRepository<Rating, BigInteger> {

    @Override
    default Rating index(Rating rating) {
        return null;
    }

    /**
     *
     */
    @Override
    default void refresh() {

    }

    /**
     * @param rating
     * @param fields
     * @param pageable
     * @return
     */
    @Override
    @Query("{\"bool\": {\"must\": [{\"match\": {\"ratings.tconst\": \"?0\"}}]}}")
    default Page<Rating> searchSimilar(Rating rating,
                                       String[] fields,
                                       Pageable pageable) {
        return null;
    }

    @Override
    @Query()
    default Class<Rating> getEntityClass() {
        return null;
    }

    @Override
    default Rating indexWithoutRefresh(Rating rating) {
        return new Rating();
    }

    /**
     * @param query
     * @return
     */
    @Override
    @Query()
    default Iterable<Rating> search(org.elasticsearch.index.query.QueryBuilder query) {
        return null;
    }

    /**
     * @param query
     * @param pageable
     * @return
     */
    @Override
    @Query()
    default Page<Rating> search(org.elasticsearch.index.query.QueryBuilder query, Pageable pageable) {
        return null;
    }

    /**
     * @param searchQuery
     * @return
     */
    @Override
    @Query()
    default Page<Rating> search(SearchQuery searchQuery) {
        return null;
    }

    @Query("{\"bool\": {\"must\": [{\"match\": {\"ratings.tconst\": \"?0\"}}]}}")
    Page<Rating> findByTconst(String tconst, Pageable pageable);

    /**
    @Query("{\"bool\": {\"must\": [{\"match\": {\"ratings.tconst\": \"?0\"}}]}}")
    Page<Rating> findByTconstUsingCustomQuery(String tconst, Pageable pageable);


    @Query("{\"bool\": {\"must\": {\"match_all\": {}}, \"filter\": {\"term\": {\"averageRatings\": \"?0\" }}}}")
    Page<Rating> findByFilteredAverageRatingQuery(String averageRating, Pageable pageable);


    @Query("{\"bool\": {\"must\": {\"match\": {\"ratings.tconst\": \"?0\"}}, \"filter\": {\"term\": {\"averageRatings\": \"?1\" }}}}")
    Page<Rating> findByTconstAndFilteredAverageRatingQuery(String tconst, String averageRating, Pageable pageable);
     */
}