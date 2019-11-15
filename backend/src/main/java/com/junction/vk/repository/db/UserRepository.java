package com.junction.vk.repository.db;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepository extends AbstractDbRepository {
    public UserRepository(JdbcTemplate fipJdbcTemplate) {
        super(fipJdbcTemplate);
    }
}
