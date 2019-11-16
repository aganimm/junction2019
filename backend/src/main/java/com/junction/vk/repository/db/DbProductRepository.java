package com.junction.vk.repository.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.api.ProductDescription;

@Repository
public class DbProductRepository extends AbstractDbRepository {
    private static final Logger logger = LoggerFactory.getLogger(DbProductRepository.class);

    private static final String SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID = "";

    public DbProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Nullable
    public ProductDescription findProductById(long productId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("product_id", productId);
            return npjtTemplate.queryForObject(SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID, namedParameters, getProductDescriptionRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke findProductById({}) with exception.", productId);
        }
        return null;
    }

    private static RowMapper<ProductDescription> getProductDescriptionRowMapper() {
        return (rs, i) -> null;
    }
}
