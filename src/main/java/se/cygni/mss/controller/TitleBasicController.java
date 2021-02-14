package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.cygni.mss.service.TitleBasicService;
import se.cygni.mss.tsv.model.TitleBasic;

@RestController
public class TitleBasicController {

    @Autowired
    private TitleBasicService titleBasicService;

    @GetMapping("/titlebasic")
    public Iterable<TitleBasic> read() {
        return titleBasicService.findAll();
    }

    @GetMapping("/titlebasic/{tconst}")
    public Iterable<TitleBasic> findByTConst(@PathVariable String tconst) {
        return titleBasicService.findByTconst(tconst);
    }

    @GetMapping("/titlebasic/search")
    public Iterable<TitleBasic> findByTconstAndTitle(
            @RequestParam(value = "tconst", required = false) String tconst,
            @RequestParam(value = "primaryTitle", required = false) String primaryTitle,
            @RequestParam(value = "originalTitle", required = false) String originalTitle
    ) {
        if (tconst != null && primaryTitle != null && originalTitle != null) {
            return titleBasicService.findByTconstAndPrimaryTitleAndOriginalTitle(tconst, primaryTitle, originalTitle);
        } else if (tconst != null) {
            return titleBasicService.findByTconst(tconst);
        } else if (primaryTitle != null) {
            return titleBasicService.findByPrimaryTitle(primaryTitle);
        } else if (originalTitle != null) {
            return titleBasicService.findByOriginalTitle(originalTitle);
        } else {
            return titleBasicService.findAll();
        }
    }
}
