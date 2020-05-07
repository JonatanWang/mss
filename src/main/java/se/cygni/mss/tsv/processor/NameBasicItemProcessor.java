package se.cygni.mss.tsv.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import se.cygni.mss.tsv.model.NameBasic;

@Slf4j
public class NameBasicItemProcessor implements ItemProcessor <NameBasic, NameBasic> {

    @Override
    public NameBasic process(NameBasic nameBasic) throws Exception {

        final String nconst = nameBasic.getNconst();
        final String primaryName = nameBasic.getPrimaryName();
        final String primaryProfession = nameBasic.getPrimaryProfession();
        final String knownForTitles = nameBasic.getKnownForTitles();
        final NameBasic transformedNameBasic = new NameBasic(nconst, primaryName, primaryProfession, knownForTitles);
        log.info("Converting (" + nameBasic + ") into (" + transformedNameBasic + ")");

        return transformedNameBasic;
    }
}
