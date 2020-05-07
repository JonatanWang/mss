package se.cygni.mss.tsv.jobs;

import javax.sql.DataSource;
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
import se.cygni.mss.tsv.listener.ImportRatingCompletionNotificationListener;
import se.cygni.mss.tsv.model.Rating;
import se.cygni.mss.tsv.processor.RatingItemProcessor;

@Configuration
@EnableBatchProcessing
public class RatingBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;


    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Rating> reader() {
        FlatFileItemReader<Rating> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("title.ratings.tsv"));

        /** Escape the 1st line of header */
        reader.setLinesToSkip(1);
        // Read a limited number of lines for testing purpose
        reader.setMaxItemCount(10);

        reader.setLineMapper(new DefaultLineMapper<Rating>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"tconst", "averageRating", "numVotes"});
                setDelimiter("\t"); /** Set '\t' as TSV delimiter*/
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Rating>() {{
                setTargetType(Rating.class);
            }});
        }});
        return reader;
    }


    @Bean
    public RatingItemProcessor processor() {
        return new RatingItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Rating> writer() {
        JdbcBatchItemWriter<Rating> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<Rating>());
        writer.setSql("DROP TABLE IF EXISTS rating;");
        writer.setSql("CREATE TABLE rating  (\n" +
                "    rating_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                "    tconst VARCHAR(20),\n" +
                "    average_rating VARCHAR(20),\n" +
                "    num_votes VARCHAR(20)\n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
        writer.setSql("INSERT INTO rating (tconst, average_rating, num_votes) VALUES (:tconst, :averageRating, :numVotes)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]


    // tag::jobstep[]
    @Bean
    public Job importRatingJob(ImportRatingCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importRatingJob").incrementer(new RunIdIncrementer())
                .listener(listener).flow(buildFactoryForRatings()).end().build();
    }

    @Bean
    public Step buildFactoryForRatings() {
        return stepBuilderFactory.get("buildFactoryForRatings").<Rating, Rating>chunk(10).reader(reader())
                .processor(processor()).writer(writer()).build();
    }
    // end::jobstep[]
}