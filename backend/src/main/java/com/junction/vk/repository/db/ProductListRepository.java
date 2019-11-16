package com.junction.vk.repository.db;

import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.repository.db.base.AbstractRepository;

@Repository
public class ProductListRepository extends AbstractRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductListRepository.class);

    private static final String SQL_SELECT_LISTS_BY_USER_ID = "select list_id, title, list_type "
            + "from list where user_id = :user_id and is_deleted = false";

    private static final String SQL_INSERT_LIST = "insert into list "
            + "(list_id, user_id, title, list_type) "
            + "values (:list_id, :user_id, :title, :list_type)";

    private static final String SQL_DELETE_LIST = "update list "
            + "set is_deleted = true where list_id = :list_id";

    private static final String SQL_INSERT_PRODUCT_TO_LIST = ""
            + "insert into list_id_to_product (list_id, product_id) values (:list_id, :product_id)";

    private static final String SQL_DELETE_PRODUCT_FROM_LIST = ""
            + "update list_id_to_product set is_deleted = true "
            + "where list_id = :list_id and product_id = :product_id";

    private static final String SQL_SELECT_PRODUCT_IDS_BY_LIST_ID = ""
            + "select product_id from list_id_to_product where list_id = :list_id";

    private static final String SQL_GET_SEQUENCE = "SELECT nextval('list_id')";

    public ProductListRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    private Long getNextId() {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_SEQUENCE, Long.class);
        } catch (DataAccessException ex) {
            logger.error("Invoke getNextId() with exception.", ex);
        }
        return null;
    }

    public Collection<ProductListItem> getProductListItemsByUserId(long userId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("user_id", userId);
            return npjtTemplate.query(SQL_SELECT_LISTS_BY_USER_ID, namedParameters, getProductListItemRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getProductListItemsByUserId({}) with exception.", userId, ex);
        }
        return Collections.emptyList();
    }

    public Long createProductList(String title, ProductListItem.ProductListType listType, long userId) {
        Long nextId = getNextId();
        if (nextId == null) {
            logger.warn("Can't get list next id.");
            return null;
        }
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("list_id", nextId);
            namedParameters.addValue("title", title);
            namedParameters.addValue("list_type", listType.getName());
            namedParameters.addValue("user_id", userId);


            if (npjtTemplate.update(SQL_INSERT_LIST, namedParameters) > 0) {
                return nextId;
            }
            logger.warn("Can't create product list: {}, {}, {}.", title, listType, userId);
        } catch (DataAccessException ex) {
            logger.error("Invoke createProductList({}, {}) with exception.", title, userId, ex);
        }
        return null;
    }

    public boolean deleteProductList(long listId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("list_id", listId);

            if (npjtTemplate.update(SQL_DELETE_LIST, namedParameters) > 0) {
                return true;
            }
            logger.warn("Can't delete list: {}.", listId);
        } catch (DataAccessException ex) {
            logger.error("Invoke deleteProductList({}) with exception.", listId, ex);
        }
        return false;
    }

    public boolean removeProductFromList(long productId, long listId) {
        try {
            if (npjtTemplate.update(SQL_DELETE_PRODUCT_FROM_LIST, getNamedParameters(productId, listId)) > 0) {
                return true;
            }
            logger.warn("Can't remove product: {} from list: {}.", productId, listId);
        } catch (DataAccessException ex) {
            logger.error("Invoke removeProductFromList({}, {}) with exception.", productId, listId, ex);
        }
        return false;
    }

    public Collection<Long> getProductsIdsByListId(long listId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("list_id", listId);
            return npjtTemplate.query(SQL_SELECT_PRODUCT_IDS_BY_LIST_ID, namedParameters, (rs, i) -> rs.getLong("product_id"));
        } catch (DataAccessException ex) {
            logger.error("Invoke getProductsIdsByListId({}) with exception.", listId, ex);
        }
        return Collections.emptyList();
    }

    public boolean addProductToList(long productId, long listId) {
        try {
            if (npjtTemplate.update(SQL_INSERT_PRODUCT_TO_LIST, getNamedParameters(productId, listId)) > 0) {
                return true;
            }
            logger.warn("Can't add product: {} to list: {}.", productId, listId);
        } catch (DataAccessException ex) {
            logger.error("Invoke addProductToList({}, {}) with exception.", productId, listId);
        }
        return false;
    }

    private static MapSqlParameterSource getNamedParameters(long productId, long listId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("product_id", productId);
        namedParameters.addValue("list_id", listId);
        return namedParameters;
    }

    private static RowMapper<ProductListItem> getProductListItemRowMapper() {
        return (rs, i) -> new ProductListItem(
                rs.getLong("list_id"),
                rs.getString("title"),
                ProductListItem.ProductListType.getProductListItemType(rs.getString("list_type")));
    }
}
