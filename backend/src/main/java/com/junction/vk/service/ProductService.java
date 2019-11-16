package com.junction.vk.service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.junction.vk.cache.ProductCache;
import com.junction.vk.cache.UserProfileCache;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.domain.ShortProductCard;
import com.junction.vk.domain.UserProfile;
import com.junction.vk.domain.api.Product;
import com.junction.vk.domain.api.ProductDescription;
import com.junction.vk.domain.api.ProductWrapper;
import com.junction.vk.repository.db.ProductListRepository;
import com.junction.vk.repository.http.VkProductRepository;

import static com.junction.vk.domain.ProductListItem.ProductListType.DEFAULT;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final UserProfileCache profileCache;
    private final ProductCache productCache;

    private final VkProductRepository productRepository;
    private final ProductListRepository productListRepository;

    public ProductService(UserProfileCache profileCache, ProductCache productCache,
            VkProductRepository productRepository, ProductListRepository productListRepository) {
        this.profileCache = profileCache;
        this.productCache = productCache;
        this.productRepository = productRepository;
        this.productListRepository = productListRepository;
    }

    public Collection<ProductCard> getProductCards(long count, long offset, String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);
        if (profile == null) {
            return Collections.emptyList();
        }

        Collection<ProductWrapper> productsPayload = productRepository
                .getProductPayloadByToken(profile.getAccessToken(), count, offset);

        return productsPayload
                .stream()
                .map(productWrapper -> {
                    Product product = productWrapper.getProduct();
                    ProductDescription description = productCache
                            .getProductDescriptionById(profile.getAccessToken(), product.getProductId());

                    logger.debug("Product: {}, description: {}.", product, description);

                    return new ProductCard(product.getProductId(), product.getCategoryId(), 0, 0,
                            product.getTitle(), description == null ? null : description.getDescription(),
                            product.getRating(), product.getImage());
                })
                .collect(Collectors.toList());
    }

    public Long createProductList(String title, ProductListItem.ProductListType productListType, String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);

        if (profile == null) {
            return null;
        }
        return productListRepository.createProductList(title, productListType, profile.getUserId());
    }

    public Collection<ProductListItem> getProductListItems(String miniAppToken) {
        UserProfile profile = getProfile(miniAppToken);
        if (profile == null) {
            return Collections.emptyList();
        }
        return productListRepository.getProductListItemsByUserId(profile.getUserId());
    }

    public boolean removeProductListById(long listId, String miniAppToken) {
        ProductListItem item = getProductListItems(miniAppToken)
                .stream()
                .filter(productListItem -> productListItem.getListId() == listId)
                .findFirst()
                .orElse(null);

        if (item == null || item.getProductListType() == DEFAULT) {
            return false;
        }
        return productListRepository.deleteProductList(listId);
    }

    public boolean removeProductFromList(long productId, long listId, String miniAppToken) {
        boolean isMatched = getProductCardsByListId(listId, miniAppToken).stream()
                .anyMatch(shortCard -> shortCard.getProductId() == productId);
        return isMatched && productListRepository.removeProductFromList(productId, listId);
    }

    public Collection<ShortProductCard> getProductCardsByListId(long listId, String miniAppToken) {
        boolean isMatched = getProductListItems(miniAppToken).stream()
                .anyMatch(shortCard -> shortCard.getListId() == listId);

        return !isMatched ? Collections.emptyList() : productListRepository
                .getProductsIdsByListId(listId)
                .stream()
                .map(productId -> new ShortProductCard(productId, "", 0, ""))
                .collect(Collectors.toList());
    }


    public boolean addProductToList(long productId, long listId, String miniAppToken) {
        boolean isExist = getProductListItems(miniAppToken).stream().anyMatch(item -> item.getListId() == listId);

        return isExist && productListRepository.addProductToList(productId, listId);
    }

    //TODO Need use spring security
    private UserProfile getProfile(String miniAppToken) {
        if (StringUtils.hasText(miniAppToken)) {

            UserProfile profile = profileCache.getUserProfileByMiniAppToken(miniAppToken);

            if (profile == null) {
                logger.debug("Can't get profile by token: {}.", miniAppToken);
            }
            return profile;
        }
        logger.debug("Mini app token is empty.");
        return null;
    }
}
