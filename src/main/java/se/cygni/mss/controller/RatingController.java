package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.mss.service.RatingService;
import se.cygni.mss.tsv.model.Rating;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/rating")
    public Iterable<Rating> read() {
        return ratingService.findAll();
    }
}
