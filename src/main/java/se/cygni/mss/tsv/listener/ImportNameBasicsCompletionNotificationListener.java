package se.cygni.mss.tsv.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import se.cygni.mss.tsv.model.NameBasic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class ImportNameBasicsCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    public ImportNameBasicsCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<NameBasic> results = jdbcTemplate
                    .query("SELECT tconst, primary_title, original_title, genres FROM title_basic",
                            new RowMapper<NameBasic>() {
                        @Override
                        public NameBasic mapRow(ResultSet rs, int row) throws SQLException {
                            return new NameBasic(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4));
                        }
                    });

            for (NameBasic nameBasic : results) {
                log.info("Found <" + nameBasic + "> in the database.");
            }

        }
    }
}