package com.junction.vk.repository.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AbstractDbRepository {
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate npjtTemplate;

    public AbstractDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.npjtTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
