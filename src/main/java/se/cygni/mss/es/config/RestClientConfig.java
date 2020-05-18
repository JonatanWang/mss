package se.cygni.mss.es.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "se.cygni.mss.es.repository")
@ComponentScan(basePackages = { "se.cygni.mss.es.service" })
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}

/** ...

    @Autowired
    RestHighLevelClient highLevelClient;

    RestClient lowLevelClient = highLevelClient.lowLevelClient();

// ...

    IndexRequest request = new IndexRequest("spring-data", "elasticsearch", randomID())
            .source(singletonMap("feature", "high-level-rest-client"))
            .setRefreshPolicy(IMMEDIATE);

    IndexResponse response = highLevelClient.index(request);
*/