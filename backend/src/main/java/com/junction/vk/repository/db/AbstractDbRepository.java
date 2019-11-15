package com.junction.vk.repository.db;

import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractDbRepository {
    protected final JdbcTemplate fipJdbcTemplate;
    protected final NamedParameterJdbcTemplate namedFipJdbcTemplate;

    public AbstractDbRepository(JdbcTemplate fipJdbcTemplate) {
        this.fipJdbcTemplate = Objects.requireNonNull(fipJdbcTemplate, "Fip jdbc template can't be null.");
        this.namedFipJdbcTemplate = new NamedParameterJdbcTemplate(fipJdbcTemplate);
    }
}
