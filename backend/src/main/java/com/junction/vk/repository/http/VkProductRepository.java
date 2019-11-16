package com.junction.vk.repository.http;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import com.junction.vk.domain.api.CategoryWrapper;
import com.junction.vk.domain.api.CategoryWrapperList;
import com.junction.vk.domain.api.ProductDescription;
import com.junction.vk.domain.api.ProductDescriptionList;
import com.junction.vk.domain.api.ProductWrapper;
import com.junction.vk.domain.api.ProductWrapperList;
import com.junction.vk.util.HttpUtils;
import com.junction.vk.util.JsonUtils;
import com.junction.vk.util.TemplateUtils;

@Repository
public class VkProductRepository extends AbstractHttpRepository {
    private static final Logger logger = LoggerFactory.getLogger(VkProductRepository.class);

    private static final String BASE_URL = "https://api.vk.com/method/";
    private static final String CREDENTIALS = "&access_token={token}&v=5.103";

    private static final String GET_FEED = BASE_URL + "junction.getFeed?count={count}&offset={offset}" + CREDENTIALS;
    private static final String GET_PRODUCT = BASE_URL + "junction.getByIds?ids={ids}" + CREDENTIALS;
    private static final String GET_CATEGORY = BASE_URL + "junction.getCategories?count={count}&offset={offset}" + CREDENTIALS;

    public VkProductRepository(HttpUtils httpUtils) {
        super(httpUtils);
    }

    public Collection<ProductWrapper> getProductPayloadByToken(String accessToken, long count, long offset) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("count", count);
            put("offset", offset);
            put("token", accessToken);
        }};

        String url = TemplateUtils.buildTemplate(GET_FEED, params);
        logger.info("Invoke getProductPayloadByToken() with url: {}.", url);

        ResponseEntity<String> feedQueryResult = this.httpUtils.jsonGetRequest(url);
        logger.debug("Get feed info for token: {} completed with status: {}.", accessToken,
                feedQueryResult.getStatusCode());

        if (feedQueryResult.getStatusCode() == HttpStatus.OK) {
            ProductWrapperList wrapperList = JsonUtils.toObject(feedQueryResult.getBody(), ProductWrapperList.class);

            if (wrapperList != null && wrapperList.getResponse() != null) {
                return wrapperList.getResponse();
            }
        }

        return Collections.emptyList();
    }

    @Nullable
    public ProductDescription getProductDescriptionByIds(String accessToken, long productId) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("ids", productId);
            put("token", accessToken);
        }};

        String url = TemplateUtils.buildTemplate(GET_PRODUCT, params);
        logger.info("Invoke getProductDescriptionByIds() with url: {}.", url);

        ResponseEntity<String> feedQueryResult = this.httpUtils.jsonGetRequest(url);
        logger.debug("Get products info ({}) for token: {} completed with status: {}.", accessToken,
                productId, feedQueryResult.getStatusCode());

        if (feedQueryResult.getStatusCode() == HttpStatus.OK) {
            ProductDescriptionList descriptions = JsonUtils.toObject(feedQueryResult.getBody(), ProductDescriptionList.class);

            if (descriptions != null) {
                return descriptions.getResponse().isEmpty() ? null : descriptions.getResponse().get(0);
            }
        }

        return null;
    }

    public Collection<CategoryWrapper> getRootCategories(String accessToken, long count, long offset) {
        Map<String, Object> params = new HashMap<String, Object>() {{
            put("count", count);
            put("offset", offset);
            put("token", accessToken);
        }};

        String url = TemplateUtils.buildTemplate(GET_CATEGORY, params);
        logger.info("Invoke getRootCategories() with url: {}.", url);

        ResponseEntity<String> feedQueryResult = this.httpUtils.jsonGetRequest(url);
        logger.debug("Get categories for token: {} completed with status: {}.", accessToken,
                feedQueryResult.getStatusCode());

        if (feedQueryResult.getStatusCode() == HttpStatus.OK) {
            CategoryWrapperList wrapperList = JsonUtils.toObject(feedQueryResult.getBody(), CategoryWrapperList.class);

            if (wrapperList != null && wrapperList.getResponse() != null) {
                List<CategoryWrapper> rootCategories = wrapperList.getResponse().stream().filter(category -> category.getParentId() == 0)
                        .collect(Collectors.toList());

                logger.debug("Root categories: {}.", rootCategories);

                return rootCategories;
            }
        }

        return Collections.emptyList();
    }
}
