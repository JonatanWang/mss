package se.cygni.mss.tsv.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import se.cygni.mss.tsv.model.TitleBasic;

@Slf4j
public class TitleBasicItemProcessor implements ItemProcessor<TitleBasic, TitleBasic> {

    @Override
    public TitleBasic process(TitleBasic titleBasic) throws Exception {
        final String tconst = titleBasic.getTconst();
        final String primaryTitle = titleBasic.getPrimaryTitle();
        final String originalTitle = titleBasic.getOriginalTitle();
        final String genres = titleBasic.getGenres();
        final TitleBasic transformedTitleBasic = new TitleBasic(tconst, primaryTitle, originalTitle, genres);
        log.info("Converting (" + titleBasic + ") into (" + transformedTitleBasic + ")");

        return transformedTitleBasic;
    }
}