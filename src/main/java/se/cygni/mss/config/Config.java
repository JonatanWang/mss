package se.cygni.mss.config;

import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Config {

  @Value("${elastic.hostname}")
  private String elasticHostname;

  @Value("${elastic.port}")
  private Integer elasticPort;

  @Value("${elastic.protocol}")
  private String elasticProtocol;

  private Gson gson;

  public Config() {
      gson = new Gson();
  }

  @Bean(destroyMethod = "close")
  public RestClient getElasticSearchClient() {
    RestClient restClient = RestClient.builder(new HttpHost(elasticHostname, elasticPort, elasticProtocol)).build();

    Request request = new Request("GET", "/_cat/indices");
    request.addParameter("v", "true");

    restClient.performRequestAsync(request, new ResponseListener() {

      @Override
      public void onSuccess(Response response) {
        try {
          String responseBody = EntityUtils.toString(response.getEntity()); 
          log.info("*** Elastic *** old index-stats:\n" + responseBody);
        } catch (Exception error) {
          log.info("*** Elastic *** ERROR parsing old index-stats: " + error.getMessage());
        }
      }

      @Override
      public void onFailure(Exception error) {
        log.info("*** Elastic *** ERROR reading old index-stats: " + error.getMessage());
      }
    });

    return restClient;
  }
  
  @Bean
  public Gson getGson(){
      return gson;
  }
}
