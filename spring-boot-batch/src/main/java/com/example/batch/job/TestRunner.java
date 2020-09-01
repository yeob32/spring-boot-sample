package com.example.batch.job;

import com.example.batch.domain.pay.Pay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Component
public class TestRunner implements ApplicationRunner {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public TestRunner(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jdbcTemplate.setDataSource(dataSource);
        List<Pay> pays = jdbcTemplate.query("SELECT id, amount, tx_name, tx_date_time FROM pay",
                (rs, rowNum) -> new Pay(rs.getLong("id"),
                        rs.getLong("amount"),
                        rs.getString("tx_name"),
                        rs.getString("tx_date_time")));

        log.info("pays size : {}", pays.size());
    }
}
