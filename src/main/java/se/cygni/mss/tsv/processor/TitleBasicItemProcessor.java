package se.cygni.mss.tsv.processor;

import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import se.cygni.mss.tsv.model.TitleBasic;

@Slf4j
public class TitleBasicItemProcessor implements ItemProcessor<TitleBasic, TitleBasic> {

  @Autowired
  private RestClient elasticSearchClient;

  @Autowired
  private Gson gson;

  @Override
  public TitleBasic process(TitleBasic titleBasic) throws Exception {
    Request request = new Request("POST", "/title-basic/_doc");
    request.setJsonEntity(gson.toJson(titleBasic));

    elasticSearchClient.performRequestAsync(request, new ResponseListener() {
      @Override
      public void onSuccess(Response response) {
        log.info("*** Elastic *** index written: " + titleBasic.getPrimaryTitle());
      }

      @Override
      public void onFailure(Exception error) {
        log.info("*** Elastic *** ERROR writing index: " + error.getMessage());
      }
    });
    
    return titleBasic;

    // final String tconst = titleBasic.getTconst();
    // final String primaryTitle = titleBasic.getPrimaryTitle();
    // final String originalTitle = titleBasic.getOriginalTitle();
    // final String genres = titleBasic.getGenres();
    // final TitleBasic transformedTitleBasic = new TitleBasic(tconst, primaryTitle, originalTitle, genres);
    // log.info("Converting (" + titleBasic + ") into (" + transformedTitleBasic + ")");

    // return transformedTitleBasic;
  }
}
