package com.junction.vk.controller;

import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.junction.vk.domain.ProductListItem;
import com.junction.vk.domain.ShortProductCard;
import com.junction.vk.domain.response.ApiResponse;
import com.junction.vk.domain.response.ApiResponseId;
import com.junction.vk.domain.response.ApiStatus;
import com.junction.vk.domain.ProductCard;
import com.junction.vk.domain.dto.CustomProductListDto;
import com.junction.vk.service.ProductService;
import com.junction.vk.util.RequestUtils;

@RestController
@RequestMapping("/api")
public class ProductController {
    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Collection<ProductCard>> getProductCards(@RequestParam Long count, @RequestParam Long offset,
            HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductCards(count, offset, RequestUtils.getMiniAppToken(request)));
    }

    @GetMapping("/product/list")
    public ResponseEntity<Collection<ProductListItem>> getProductListItems(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductListItems(RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/add")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createProductList(@RequestBody CustomProductListDto customProductListDto,
            HttpServletRequest request) {
        Long listId = productService.createProductList(customProductListDto.getTitle(),
                RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(listId == null ? ApiResponse.of(ApiStatus.INTERNAL_ERROR) :
                ApiResponseId.of(ApiStatus.LIST_CREATED, listId));
    }

    @PostMapping("/product/list/{listId}/remove")
    public ResponseEntity<?> removeProductList(@PathVariable long listId, HttpServletRequest request) {
        boolean isRemoved = productService.removeProductListById(listId, RequestUtils.getMiniAppToken(request));
        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.LIST_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }

    @GetMapping("/product/list/{listId}")
    public ResponseEntity<Collection<ShortProductCard>> getProductCardsByListId(HttpServletRequest request,
            @PathVariable long listId) {
        return ResponseEntity.ok(productService.getProductCardsByListId(listId, RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/{listId}/product/{productId}/remove")
    public ResponseEntity<?> removeProductFromList(@PathVariable long productId, HttpServletRequest request,
            @PathVariable long listId) {
        boolean isRemoved = productService.removeProductFromList(productId, listId, RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.PRODUCT_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }
}
