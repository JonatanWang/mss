package se.cygni.mss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.mss.service.PrincipalService;
import se.cygni.mss.tsv.model.Principal;

@RestController
public class PrincipalController {

    @Autowired
    private PrincipalService principalService;

    @GetMapping("/principal")
    public Iterable<Principal> read() {
        return principalService.findAll();
    }

    @GetMapping("/principal/tconst/{tconst}")
    public Iterable<Principal> findByTconst(@PathVariable String tconst) {
        return principalService.findByTconst(tconst);
    }

    @GetMapping("/principal/nconst/{nconst}")
    public Iterable<Principal> findByNconst(@PathVariable String nconst) {
        return principalService.findByNconst(nconst);
    }

    @GetMapping("/principal/search")
    public Iterable<Principal> findByTconstAndNconst(
            @RequestParam(value = "tconst", required = false) String tconst,
            @RequestParam(value = "nconst", required = false) String nconst
    ) {
        if (tconst != null && nconst != null) {
            return principalService.findByTconstAndNconst(tconst, nconst);
        } else if (tconst != null) {
            return principalService.findByTconst(tconst);
        } else if (nconst != null) {
            return principalService.findByNconst(nconst);
        } else {
            return principalService.findAll();
        }
    }
}
