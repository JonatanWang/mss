package se.cygni.mss.tsv.listener;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import se.cygni.mss.tsv.model.Rating;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Rating> results = jdbcTemplate
                    .query("SELECT tconst, average_rating, num_votes FROM rating", new RowMapper<Rating>() {
                        @Override
                        public Rating mapRow(ResultSet rs, int row) throws SQLException {
                            return new Rating(rs.getString(1), rs.getString(2),  rs.getString(3));
                        }
                    });

            for (Rating rating : results) {
                log.info("Found <" + rating + "> in the database.");
            }

        }
    }
}