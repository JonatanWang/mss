package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.mss.service.RatingService;
import se.cygni.mss.tsv.model.Rating;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/rating")
    public Iterable<Rating> read() {
        return ratingService.findAll();
    }

    @GetMapping("/rating/{tconst}")
    Iterable<Rating> findByTconst(@PathVariable String tconst) {
        return ratingService.findByTconst(tconst);
    }

    @GetMapping("/rating/search")
    Iterable<Rating> findByQuery(@RequestParam(value = "tconst", required = false) String tconst,
                                 @RequestParam(value = "averageRating", required = false) String averageRating) {
        if (tconst != null && averageRating != null) {
            return ratingService.findByTconstAndAverageRating(tconst, averageRating);
        } else if (tconst != null) {
            return ratingService.findByTconst(tconst);
        } else if (averageRating != null) {
            return ratingService.findByAverageRating(averageRating);
        } else {
            return ratingService.findAll();
        }
    }

    @GetMapping("/wrong")
    public Rating getSomethingWrong() throws ValidationException {

        throw new ValidationException("Something is wrong.");
    }
}
