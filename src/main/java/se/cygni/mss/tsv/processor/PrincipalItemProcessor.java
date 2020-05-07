package se.cygni.mss.tsv.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import se.cygni.mss.tsv.model.Principal;
import se.cygni.mss.tsv.model.Rating;

@Slf4j
public class PrincipalItemProcessor implements ItemProcessor<Principal, Principal> {

    @Override
    public Principal process(Principal principal) throws Exception {
        final String tconst = principal.getTconst();
        final String nconst = principal.getNconst();
        final String category = principal.getCategory();
        final String characters = principal.getCharacters();
        final Principal transformedPrincipal = new Principal(tconst, nconst, category, characters);
        log.info("Converting (" + principal + ") into (" + transformedPrincipal + ")");

        return transformedPrincipal;
    }
}