package com.junction.vk.repository.db;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.Feed;
import com.junction.vk.repository.db.base.AbstractRepository;

@Repository
public class FeedRepository extends AbstractRepository {
    private static final Logger logger = LoggerFactory.getLogger(FeedRepository.class);

    private static final String SQL_INSERT_FEED = "insert into feed (user_id, product_id, is_liked) "
            + "values (:user_id, :product_id, :is_liked)";

    private static final String SQL_SELECT_ALL_FEED = "select user_id, product_id, is_liked "
            + "from feed";

    public FeedRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public boolean setFeed(long userId, long productId, boolean isLicked) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("user_id", userId);
            namedParameters.addValue("product_id", productId);
            namedParameters.addValue("is_licked", isLicked);

            if (npjtTemplate.update(SQL_INSERT_FEED, namedParameters) > 0) {
                return true;
            }
            logger.error("Can't insert feed data, user id {}.", userId);
        } catch (DataAccessException ex) {
            logger.error("Invoke setFeed({}, {}, {}) with exception.", userId, productId, isLicked);
        }
        return false;
    }

    public Map<Long, List<Feed>> getUsersFeed() {
        try {
            List<Feed> feeds = npjtTemplate.query(SQL_SELECT_ALL_FEED, getFeedRowMapper());

            return feeds.stream().collect(Collectors.groupingBy(Feed::getUserId));
        } catch (DataAccessException ex) {
            logger.error("Invoke getUsersFeed() with exception.", ex);
        }
        return Collections.emptyMap();
    }

    private static RowMapper<Feed> getFeedRowMapper() {
        return (rs, i) -> new Feed(
                rs.getLong("user_id"),
                rs.getLong("product_id"),
                rs.getBoolean("is_liked")
        );
    }
}
