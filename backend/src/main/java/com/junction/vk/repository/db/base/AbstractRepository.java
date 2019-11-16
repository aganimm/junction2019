package com.junction.vk.repository.db.base;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AbstractRepository {
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate npjtTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.npjtTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
}
