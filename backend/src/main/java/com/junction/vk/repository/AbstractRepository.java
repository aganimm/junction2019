package com.junction.vk.repository;

import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractRepository {
    protected final JdbcTemplate fipJdbcTemplate;
    protected final NamedParameterJdbcTemplate namedFipJdbcTemplate;

    public AbstractRepository(JdbcTemplate fipJdbcTemplate) {
        this.fipJdbcTemplate = Objects.requireNonNull(fipJdbcTemplate, "Fip jdbc template can't be null.");
        this.namedFipJdbcTemplate = new NamedParameterJdbcTemplate(fipJdbcTemplate);
    }
}
