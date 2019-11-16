package com.junction.vk.repository.db;

import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.repository.db.base.AbstractRepository;

@Repository
public class ProductRepository extends AbstractRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private static final String SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID = ""
            + "select product_id, price, title, rating, image from products";

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Collection<ProductCard> findProducts() {
        try {
            return npjtTemplate.query(SQL_SELECT_PRODUCT_DESCRIPTION_BY_ID, getProductDescriptionRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke findProductById() with exception.", ex);
        }
        return Collections.emptyList();
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
