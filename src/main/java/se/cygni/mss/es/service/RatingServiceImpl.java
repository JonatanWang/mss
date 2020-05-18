package se.cygni.mss.es.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import se.cygni.mss.es.repository.RatingRepository;
import se.cygni.mss.service.RatingService;
import se.cygni.mss.tsv.model.Rating;

@Repository
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Iterable<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Page<Rating> findByTconst(String tconst, Pageable pageable) {
        return ratingRepository.findByTconst(tconst, pageable);
    }

    @Override
    public long count() {
        return ratingRepository.count();
    }

    @Override
    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }

}
