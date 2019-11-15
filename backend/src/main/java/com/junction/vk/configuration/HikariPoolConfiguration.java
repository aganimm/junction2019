package com.junction.vk.configuration;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class HikariPoolConfiguration {
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUser;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${jdbc.poolSize:30}")
    private int jdbcMaxPoolSize;
    @Value("${jdbc.minimumIdle:0}")
    private int jdbcMinimumIdle;

    @Bean
    public DataSource defaultDataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        if (StringUtils.isEmpty(jdbcUrl)) {
            throw new IllegalArgumentException("Can't create data source");
        } else {
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(jdbcUser);
            config.setPassword(jdbcPassword);
            config.setMaximumPoolSize(jdbcMaxPoolSize);
            config.setMinimumIdle(jdbcMinimumIdle);
            config.setAutoCommit(true);
        }
        // default tuning
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}
