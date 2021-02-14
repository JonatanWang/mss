package se.cygni.mss.tsv.jobs;

// import org.apache.http.util.EntityUtils;
// import org.elasticsearch.client.Request;
// import org.elasticsearch.client.Response;
// import org.elasticsearch.client.RestClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import se.cygni.mss.tsv.listener.ImportTitleBasicsCompletionNotificationListener;
import se.cygni.mss.tsv.model.TitleBasic;
import se.cygni.mss.tsv.processor.TitleBasicItemProcessor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class TitleBasicBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;
    // @Autowired
    // private RestClient elasticSearchClient;

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<TitleBasic> readerTitleBasic() {
        FlatFileItemReader<TitleBasic> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("title.basics.tsv"));

        /** Escape the 1st line of header */
        reader.setLinesToSkip(1);
        // Read a limited number of lines for testing purpose
        reader.setMaxItemCount(10);

        reader.setLineMapper(new DefaultLineMapper<TitleBasic>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("tconst",
                        "titleType",
                        "primaryTitle",
                        "originalTitle",
                        "isAdult",
                        "startYear",
                        "endYear",
                        "runtimeMinutes",
                        "genres");

                /** Set '\t' as TSV delimiter*/
                setDelimiter("\t");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<TitleBasic>() {{
                setTargetType(TitleBasic.class);
            }});
        }});

        return reader;
    }


    @Bean
    public TitleBasicItemProcessor processorTitleBasic() {
        return new TitleBasicItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TitleBasic> writerTitleBasic() {
        JdbcBatchItemWriter<TitleBasic> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        // writer.setSql("DROP TABLE IF EXISTS title_basic;");
        // writer.setSql("CREATE TABLE title_basic  (\n" +
        //         "    title_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
        //         "    tconst VARCHAR(20),\n" +
        //         "    title_type VARCHAR(20),\n" +
        //         "    primary_title VARCHAR(255),\n" +
        //         "    original_title VARCHAR(255),\n" +
        //         "    is_adult VARCHAR(20),\n" +
        //         "    start_year VARCHAR(20),\n" +
        //         "    end_year VARCHAR(20),\n" +
        //         "    runtime_minutes VARCHAR(20),\n" +
        //         "    genres VARCHAR(255)\n" +
        //         ")ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
        writer.setSql("INSERT INTO title_basic (" +
                "tconst, " +
                "title_type, " +
                "primary_title, " +
                "original_title, " +
                "is_adult, " +
                "start_year, " +
                "end_year, " +
                "runtime_minutes, " +
                "genres) " +
                "VALUES (" +
                ":tconst, " +
                ":titleType, " +
                ":primaryTitle, " +
                ":originalTitle, " +
                ":isAdult, " +
                ":startYear, " +
                ":endYear, " +
                ":runtimeMinutes, " +
                ":genres)");
        writer.setDataSource(dataSource);

        // try {
        //   Request request = new Request("GET", "/");
        //   request.addParameter("pretty", "true");
        //   Response response = elasticSearchClient.performRequest(request);
        //   String responseBody = EntityUtils.toString(response.getEntity());
        //   System.out.println("Elastic Search root query:" + responseBody);
        // } catch (Exception error) {
        //   System.out.println("Elastic Search root query failed:" + error.getMessage());
        // }
      
        return writer;
    }
    // end::readerwriterprocessor[]


    // tag::jobstep[]
    @Bean
    public Job importTitleBasicJob(ImportTitleBasicsCompletionNotificationListener listener) {
      JobBuilder jobBuilder = jobBuilderFactory
        .get("importTitleBasicJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener);
      Job job = jobBuilder
        .flow(buildFactoryForTitleBasics())
        .end()
        .build();
      return job;
    }

    @Bean
    public Step buildFactoryForTitleBasics() {
      SimpleStepBuilder<TitleBasic, TitleBasic> simpleStepBuilder = stepBuilderFactory
        .get("buildFactoryForTitleBasics")
        .<TitleBasic, TitleBasic>chunk(10);
      TaskletStep step = simpleStepBuilder
        .reader(readerTitleBasic())
        .processor(processorTitleBasic())
        .writer(writerTitleBasic())
        .build();
      return step;
    }
    // end::jobstep[]
}