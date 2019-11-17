package com.junction.vk.controller;

import io.swagger.annotations.ApiOperation;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.domain.dto.FeedDto;
import com.junction.vk.domain.response.ApiResponse;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.service.ProductService;
import com.junction.vk.util.RequestUtils;

@RestController
@RequestMapping("/api")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get next product card")
    public ResponseEntity<Collection<ProductCard>> getProductCards(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductCards(RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/feed")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Set feed for product id")
    public ResponseEntity<ApiResponse> updateFeed(@RequestBody FeedDto feedDto, HttpServletRequest request) {
        logger.debug("Invoke updateFeed: {}.", feedDto);
        boolean isUpdated = productService.updateFeed(feedDto.getProductId(), feedDto.getFeed(),
                RequestUtils.getMiniAppToken(request));
        return ResponseEntity.ok(ApiResponse.of(isUpdated ? ApiStatus.USER_CREATED : ApiStatus.INTERNAL_ERROR));
    }

    @GetMapping("/product/list")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get all user lists")
    public ResponseEntity<Collection<ProductCard>> getProductListItems(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductCardsInCart(RequestUtils.getMiniAppToken(request)));
    }
}
