package se.cygni.mss.tsv.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import se.cygni.mss.tsv.model.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
@Slf4j
public class ImportPrincipalsCompletionNotificationListener extends JobExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    public ImportPrincipalsCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            List<Principal> results = jdbcTemplate
                    .query("SELECT tconst, nconst, category, characters FROM principal", new RowMapper<Principal>() {
                        @Override
                        public Principal mapRow(ResultSet rs, int row) throws SQLException {
                            return new Principal(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                        }
                    });

            for (Principal principal : results) {
                log.info("Found <" + principal + "> in the database.");
            }

        }
    }
}