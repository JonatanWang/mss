package se.cygni.mss.tsv.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
import se.cygni.mss.tsv.listener.ImportNameBasicsCompletionNotificationListener;
import se.cygni.mss.tsv.model.NameBasic;
import se.cygni.mss.tsv.processor.NameBasicItemProcessor;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class NameBasicBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;


    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<NameBasic> readerNameBasic() {
        FlatFileItemReader<NameBasic> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("name.basics.tsv"));

        /** Escape the 1st line of header */
        reader.setLinesToSkip(1);
        // Read a limited number of lines for testing purpose
        reader.setMaxItemCount(10);

        reader.setLineMapper(new DefaultLineMapper<NameBasic>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("nconst",
                        "primaryName",
                        "birthYear",
                        "deathYear",
                        "primaryProfession",
                        "knownForTitles");

                /** Set '\t' as TSV delimiter*/
                setDelimiter("\t");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<NameBasic>() {{
                setTargetType(NameBasic.class);
            }});
        }});

        return reader;
    }


    @Bean
    public NameBasicItemProcessor processorNameBasic() {
        return new NameBasicItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<NameBasic> writerNameBasic() {
        JdbcBatchItemWriter<NameBasic> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("DROP TABLE IF EXISTS name_basic;");
        /**
        writer.setSql("CREATE TABLE name_basic  (\n" +
                "    name_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                "    nconst VARCHAR(20),\n" +
                "    primary_name VARCHAR(255),\n" +
                "    birth_year VARCHAR(20),\n" +
                "    death_year VARCHAR(20),\n" +
                "    primary_profession VARCHAR(255),\n" +
                "    known_for_titles VARCHAR(255) \n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
         */
        writer.setSql("INSERT INTO name_basic (" +
                "nconst, " +
                "primary_name, " +
                "birth_year, " +
                "death_year, " +
                "primary_profession, " +
                "known_for_titles) " +
                "VALUES (" +
                ":nconst, " +
                ":primaryName, " +
                ":birthYear, " +
                ":deathYear, " +
                ":primaryProfession, " +
                ":knownForTitles)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]


    // tag::jobstep[]
    @Bean
    public Job importNameBasicJob(ImportNameBasicsCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importNameBasicJob").incrementer(new RunIdIncrementer())
                .listener(listener).flow(buildFactoryForNameBasics()).end().build();
    }

    @Bean
    public Step buildFactoryForNameBasics() {
        return stepBuilderFactory.get("buildFactoryForNameBasics").<NameBasic, NameBasic>chunk(10).reader(readerNameBasic())
                .processor(processorNameBasic()).writer(writerNameBasic()).build();
    }
    // end::jobstep[]
}