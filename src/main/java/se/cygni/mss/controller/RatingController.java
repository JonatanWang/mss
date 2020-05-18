package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.mss.tsv.model.Rating;

@RestController
@RequestMapping("/")
public class RatingController {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @GetMapping("/rating/{id}")
    public Rating findById(@PathVariable("id")  String id) {
        return elasticsearchOperations
                .queryForObject(GetQuery.getById(id.toString()), Rating.class);
    }
}