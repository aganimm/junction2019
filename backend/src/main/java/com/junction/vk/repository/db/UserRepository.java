package com.junction.vk.repository.db;

import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.LookingForType;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.repository.db.base.AbstractRepository;

@Repository
public class UserRepository extends AbstractRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private static final String SQL_SELECT_USER = "select user_id, mini_app_token, access_token, looking_for,"
            + "description, sex from user_profile ";

    private static final String SQL_SELECT_USER_BY_ID = SQL_SELECT_USER
            + "where user_id = :user_id";

    private static final String SQL_SELECT_USER_BY_MINI_APP_TOKEN = SQL_SELECT_USER
            + "where mini_app_token = :mini_app_token";

    private static final String SQL_INSERT_USER = "insert into user_profile (user_id, mini_app_token, access_token) "
            + "values (:user_id, :mini_app_token, :access_token)";

    private static final String SQL_UPDATE_USER_BY_ID = "update user_profile "
            + "set mini_app_token = :mini_app_token, access_token = :access_token "
            + "where user_id = :user_id";

    private static final String SQL_UPDATE_PERSONAL_INFO = "update user_profile "
            + "set looking_for = :looking_for, description = :description, sex = :sex "
            + "where user_id = :user_id";

    public UserRepository(JdbcTemplate fipJdbcTemplate) {
        super(fipJdbcTemplate);
    }

    public boolean existUserById(long userId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("user_id", userId);

            return npjtTemplate.query(SQL_SELECT_USER_BY_ID, namedParameters, getUserProfileRowMapper()).size() != 0;
        } catch (DataAccessException ex) {
            logger.error("Invoke existUserById({}).", userId, ex);
        }
        return true;
    }

    public boolean updateUser(long userId, String miniAppToken, String accessToken) {
        try {
            if (npjtTemplate.update(SQL_UPDATE_USER_BY_ID, getNamedParameters(userId, miniAppToken, accessToken,
                    null, null, null)) > 0) {
                return true;
            }
            logger.warn("Can't update user with id: {}.", userId);
        } catch (DataAccessException ex) {
            logger.error("Invoke updateUser({}, {}, {}).", userId, miniAppToken, accessToken, ex);
        }
        return false;
    }

    public boolean updatePersonalInfo(long userId, LookingForType lookingFor, String description, String sex) {
        try {
            if (npjtTemplate.update(SQL_UPDATE_PERSONAL_INFO, getNamedParameters(userId, null, null,
                    lookingFor, description, sex)) > 0) {
                return true;
            }
            logger.warn("Can't update user personal info with id: {}.", userId);
        } catch (DataAccessException ex) {
            logger.error("Invoke updatePersonalInfo({}, {}, {}).", userId, lookingFor, description, ex);
        }
        return false;
    }

    public boolean createUser(long userId, String miniAppToken, String accessToken) {
        try {
            if (npjtTemplate.update(SQL_INSERT_USER, getNamedParameters(userId, miniAppToken, accessToken,
                    null, null, null)) > 0) {
                return true;
            }
            logger.warn("Can't create user with id: {}.", userId);
        } catch (DataAccessException ex) {
            logger.error("Invoke createUser({}, {}, {}).", userId, miniAppToken, accessToken, ex);
        }
        return false;
    }

    @Nullable
    public UserProfile findUserProfileByMiniAppToken(String miniAppToken) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("mini_app_token", miniAppToken);
            return npjtTemplate.queryForObject(SQL_SELECT_USER_BY_MINI_APP_TOKEN, namedParameters, getUserProfileRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getUserProfileByMiniAppToken({}) with exception.", miniAppToken, ex);
        }
        return null;
    }

    private static MapSqlParameterSource getNamedParameters(long userId, @Nullable String miniAppToken,
            @Nullable String accessToken, @Nullable LookingForType lookingFor, @Nullable String description,
            @Nullable String sex) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("user_id", userId);
        if (miniAppToken != null) {
            namedParameters.addValue("mini_app_token", miniAppToken);
        }
        if (accessToken != null) {
            namedParameters.addValue("access_token", accessToken);
        }
        if (lookingFor != null) {
            namedParameters.addValue("looking_for", lookingFor.getName());
        }
        if (description != null) {
            namedParameters.addValue("description", description);
        }
        if (sex != null) {
            namedParameters.addValue("sex", sex);
        }
        return namedParameters;
    }

    private static RowMapper<UserProfile> getUserProfileRowMapper() {
        return (rs, i) -> new UserProfile(
                rs.getLong("user_id"),
                rs.getString("mini_app_token"),
                rs.getString("access_token"),
                LookingForType.findTypeByName(rs.getString("looking_for")),
                rs.getString("description"),
                rs.getString("sex")
        );
    }
}
