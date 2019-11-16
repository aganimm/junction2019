package com.junction.vk.repository.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository extends AbstractDbRepository {
    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }
}
