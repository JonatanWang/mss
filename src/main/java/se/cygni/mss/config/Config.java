package se.cygni.mss.config;

import com.google.gson.Gson;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Value("${elastic.hostname}")
  private String elasticHostname;

  @Value("${elastic.port}")
  private Integer elasticPort;

  @Value("${elastic.protocol}")
  private String elasticProtocol;

  @Bean(destroyMethod = "close")
  public RestClient getElasticSearchClient() {
    RestClient restClient = RestClient.builder(new HttpHost(elasticHostname, elasticPort, elasticProtocol)).build();
    return restClient;
  }
  
  @Bean
  public Gson getGson(){
      return new Gson();
  }
}
