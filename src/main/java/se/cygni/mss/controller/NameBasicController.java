package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.mss.service.NameBasicService;
import se.cygni.mss.tsv.model.NameBasic;

@RestController
public class NameBasicController {

    @Autowired
    private NameBasicService nameBasicService;

    @GetMapping("/namebasic")
    public Iterable<NameBasic> read() {
        return nameBasicService.findAll();
    }

    @GetMapping("/namebasic/{nconst}")
    public Iterable<NameBasic> findByNconst(@PathVariable String nconst) {
        return nameBasicService.findByNconst(nconst);
    }

    @GetMapping("/namebasic/search")
    public Iterable<NameBasic> findByNconstAndPrimaryName(
            @RequestParam(value = "nconst", required = false) String nconst,
            @RequestParam(value = "primaryName", required = false) String primaryName) {
        if (nconst != null && primaryName != null) {
            return nameBasicService.findByNconstAndPrimaryName(nconst, primaryName);
        } else if (nconst != null) {
            return nameBasicService.findByNconst(nconst);
        } else if (primaryName != null) {
            return nameBasicService.findByPrimaryName(primaryName);
        } else {
            return nameBasicService.findAll();
        }
    }
}
