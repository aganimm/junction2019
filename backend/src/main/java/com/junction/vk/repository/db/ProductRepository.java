package com.junction.vk.repository.db;

import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.repository.db.base.AbstractRepository;

@Repository
public class ProductRepository extends AbstractRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private static final String SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID = "";

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Nullable
    public ProductCard findProductById(long productId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("product_id", productId);
            return npjtTemplate
                    .queryForObject(SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID, namedParameters, getProductDescriptionRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke findProductById({}) with exception.", productId);
        }
        return null;
    }

    private static RowMapper<ProductCard> getProductDescriptionRowMapper() {
        return (rs, i) -> new ProductCard(
                rs.getLong("product_id"),
                rs.getLong("price"),
                rs.getString("title"),
                rs.getDouble("rating"),
                rs.getString("image")
        );
    }
}
