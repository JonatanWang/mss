package se.cygni.mss.tsv.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import se.cygni.mss.tsv.model.Rating;

@Slf4j
public class RatingItemProcessor implements ItemProcessor<Rating, Rating> {

    @Override
    public Rating process(Rating rating) throws Exception {
        final String tconst = rating.getTconst();
        final String averageRating = rating.getAverageRating();
        final String numVotes = rating.getNumVotes();
        final Rating transformedRating = new Rating(tconst, averageRating, numVotes);
        log.info("Converting (" + rating + ") into (" + transformedRating + ")");

        return transformedRating;
    }
}