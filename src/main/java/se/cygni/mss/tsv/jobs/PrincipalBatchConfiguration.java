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
import se.cygni.mss.tsv.listener.ImportPrincipalsCompletionNotificationListener;
import se.cygni.mss.tsv.model.Principal;
import se.cygni.mss.tsv.processor.PrincipalItemProcessor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class PrincipalBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;


    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Principal> readerPrincipal() {
        FlatFileItemReader<Principal> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("title.principals.tsv"));

        /** Escape the 1st line of header */
        reader.setLinesToSkip(1);
        // Read a limited number of lines for testing purpose
        reader.setMaxItemCount(10);

        reader.setLineMapper(new DefaultLineMapper<Principal>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"tconst", "ordering", "nconst", "category", "job", "characters"});
                setDelimiter("\t"); /** Set '\t' as TSV delimiter*/
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Principal>() {{
                setTargetType(Principal.class);
            }});
        }});
        return reader;
    }


    @Bean
    public PrincipalItemProcessor processorPrincipal() {
        return new PrincipalItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Principal> writerPrincipal() {
        JdbcBatchItemWriter<Principal> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<Principal>());
        // writer.setSql("DROP TABLE IF EXISTS principal;");
        // writer.setSql("CREATE TABLE principal  (\n" +
        //         "    principal_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
        //         "    tconst VARCHAR(20),\n" +
        //         "    ordering VARCHAR(20),\n" +
        //         "    nconst VARCHAR(20),\n" +
        //         "    category VARCHAR(255),\n" +
        //         "    job VARCHAR(255),\n" +
        //         "    characters VARCHAR(1000)\n" +
        //         ")ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;");
        writer.setSql("INSERT INTO principal (tconst, ordering, nconst, category, job, characters) " +
                "VALUES (:tconst, :ordering, :nconst, :category, :job, :characters)");
        writer.setDataSource(dataSource);
        return writer;
    }
    // end::readerwriterprocessor[]


    // tag::jobstep[]
    @Bean
    public Job importPrincipalJob(ImportPrincipalsCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importPrincipalJob").incrementer(new RunIdIncrementer())
                .listener(listener).flow(buildFactoryForPrincipals()).end().build();
    }

    @Bean
    public Step buildFactoryForPrincipals() {
        return stepBuilderFactory.get("buildFactoryForPrincipals").<Principal, Principal>chunk(10).reader(readerPrincipal())
                .processor(processorPrincipal()).writer(writerPrincipal()).build();
    }
    // end::jobstep[]
}