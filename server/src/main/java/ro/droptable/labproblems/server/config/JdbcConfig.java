package ro.droptable.labproblems.server.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.postgresql.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by vlad on 04.04.2017.
 */
@Configuration
public class JdbcConfig {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:postgresql://localhost:5432/labproblems");
//        dataSource.setUsername(System.getProperty("username"));
//        dataSource.setPassword(System.getProperty("password"));
        dataSource.setUsername("postgres");
        dataSource.setPassword("admin");
        dataSource.setInitialSize(2);
        dataSource.setMaxActive(5);
        return dataSource;
    }
}
