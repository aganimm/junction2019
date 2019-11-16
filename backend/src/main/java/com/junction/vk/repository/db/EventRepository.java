package com.junction.vk.repository.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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

@Repository
public class EventRepository extends AbstractDbRepository {
    private static final Logger logger = LoggerFactory.getLogger(EventRepository.class);

    private static final String SQL_SELECT_PRODUCT_IDS_BY_EVENT_ID = "";

    private static final String SQL_INSERT_FEED = "";

    private static final String SQL_SELECT_ALL_FEED = "";

    public EventRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public Collection<Long> getProductIdsByEventId(long eventId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("event_id", eventId);
            return npjtTemplate.query(SQL_SELECT_PRODUCT_IDS_BY_EVENT_ID, namedParameters,
                    (rs, i) -> rs.getLong(""));
        } catch (DataAccessException ex) {
            logger.error("Invoke getProductIdsByEventId({}) with exception.", eventId);
        }
        return Collections.emptyList();
    }

    public boolean setFeed(long userId, long eventId, long productId, boolean isLicked) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("userId", userId);
            namedParameters.addValue("eventId", eventId);
            namedParameters.addValue("productId", productId);
            namedParameters.addValue("isLicked", isLicked);

            if (npjtTemplate.update(SQL_INSERT_FEED, namedParameters) > 0) {
                return true;
            }
            logger.error("Can't insert feed data, user id {}, event id {}.", userId, eventId);
        } catch (DataAccessException ex) {
            logger.error("Invoke setFeed({}, {}, {}, {}) with exception.", userId, eventId, productId, isLicked);
        }
        return false;
    }

    public Map<Long, List<Feed>> getUsersFeed(long eventId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("event_id", eventId);
            List<Feed> feeds = npjtTemplate.query(SQL_SELECT_ALL_FEED, namedParameters, getFeedRowMapper());

            return feeds.stream().collect(Collectors.groupingBy(Feed::getUserId));
        } catch (DataAccessException ex) {
            logger.error("Invoke getUsersFeed() with exception.", ex);
        }
        return Collections.emptyMap();
    }

    private static RowMapper<Feed> getFeedRowMapper() {
        return new RowMapper<Feed>() {
            @Override
            public Feed mapRow(ResultSet rs, int i) throws SQLException {
                return new Feed(
                        rs.getLong(""),
                        rs.getLong(""),
                        rs.getBoolean("")
                );
            }
        };
    }
}
