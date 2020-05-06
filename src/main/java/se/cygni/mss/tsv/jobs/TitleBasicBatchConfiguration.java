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


    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<TitleBasic> readerTitleBasic() {
        FlatFileItemReader<TitleBasic> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("title.basics.tsv"));

        /** Escape the 1st line of header */
        reader.setLinesToSkip(1);
        reader.setMaxItemCount(1000);

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
        writer.setSql("DROP TABLE IF EXISTS title_basic;");
        writer.setSql("CREATE TABLE title_basic  (\n" +
                "    basic_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                "    tconst VARCHAR(20),\n" +
                "    title_type VARCHAR(20),\n" +
                "    primary_title VARCHAR(255),\n" +
                "    original_title VARCHAR(255),\n" +
                "    is_adult VARCHAR(20),\n" +
                "    start_year VARCHAR(20),\n" +
                "    end_year VARCHAR(20),\n" +
                "    runtime_minutes VARCHAR(20),\n" +
                "    genres VARCHAR(255)\n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
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
        return writer;
    }
    // end::readerwriterprocessor[]


    // tag::jobstep[]
    @Bean
    public Job importTitleBasicJob(ImportTitleBasicsCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importTitleBasicJob").incrementer(new RunIdIncrementer())
                .listener(listener).flow(step2()).end().build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<TitleBasic, TitleBasic>chunk(10).reader(readerTitleBasic())
                .processor(processorTitleBasic()).writer(writerTitleBasic()).build();
    }
    // end::jobstep[]
}