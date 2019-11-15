package com.junction.vk.repository.http;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.junction.vk.domain.api.ProductDescription;
import com.junction.vk.domain.api.ProductWrapper;
import com.junction.vk.util.HttpUtils;
import com.junction.vk.util.JsonUtils;
import com.junction.vk.util.TemplateUtils;

public class VkFeedRepository extends AbstractHttpRepository {
    private static final Logger logger = LoggerFactory.getLogger(VkFeedRepository.class);

    private static final String BASE_URL = "https://api.vk.com/method/";
    private static final String CREDENTIALS = "&access_token={token}&v=5.103";

    private static final String GET_FEED = BASE_URL + "junction.getFeed?count={count}&offset={offset}" + CREDENTIALS;
    private static final String GET_PRODUCT = BASE_URL + "junction.getByIds={ids}" + CREDENTIALS;


    public VkFeedRepository(HttpUtils httpUtils) {
        super(httpUtils);
    }

    public Collection<ProductWrapper> getFeedPayloadByToken(String token, long count, long offset) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("count", count);
            put("offset", offset);
            put("token", token);
        }};

        String template = TemplateUtils.buildTemplate(GET_FEED, params);

        ResponseEntity<String> feedQueryResult = this.httpUtils.jsonGetRequest(template);
        logger.debug("Get feed info for token: {} completed with status: {}.", token,
                feedQueryResult.getStatusCode());

        if (feedQueryResult.getStatusCode() == HttpStatus.OK) {
            return JsonUtils.toList(feedQueryResult.getBody(), ProductWrapper.class);
        }

        return Collections.emptyList();
    }

    public Map<String, ProductDescription> getProductDescriptionByIds(String token, String... ids) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("ids", String.join(",", ids));
            put("token", token);
        }};

        String template = TemplateUtils.buildTemplate(GET_PRODUCT, params);

        ResponseEntity<String> feedQueryResult = this.httpUtils.jsonGetRequest(template);
        logger.debug("Get products info ({}) for token: {} completed with status: {}.", token,
                Arrays.asList(ids), feedQueryResult.getStatusCode());

        if (feedQueryResult.getStatusCode() == HttpStatus.OK) {
            List<ProductDescription> productWrappers = JsonUtils.toList(feedQueryResult.getBody(), ProductDescription.class);

            return productWrappers
                    .stream()
                    .collect(Collectors.toMap(ProductDescription::getProductId, Function.identity()));
        }

        return Collections.emptyMap();
    }
}
