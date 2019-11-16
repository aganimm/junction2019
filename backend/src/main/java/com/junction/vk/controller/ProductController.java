package com.junction.vk.controller;

import io.swagger.annotations.ApiOperation;

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
import com.junction.vk.domain.dto.FeedDto;
import com.junction.vk.domain.dto.ProductIdDto;
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
    @ApiOperation(value = "Get all product cards")
    public ResponseEntity<ProductCard> getProductCards(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductCard(RequestUtils.getMiniAppToken(request))));
    }

    @PostMapping("/feed")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Set feed for product id")
    public ResponseEntity<ApiResponse> updateFeed(@RequestBody FeedDto feedDto, HttpServletRequest request) {
        return ResponseEntity.ok(productService.updateFeed(feedDto.getProductId(), feedDto.isLicked(),
                RequestUtils.getMiniAppToken(request)));
    }

    @GetMapping("/product/list")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Get all user lists")
    public ResponseEntity<Collection<ProductListItem>> getProductListItems(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductListItems(RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/add")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Add new list for user")
    public ResponseEntity<? extends ApiResponse> createProductList(@RequestBody CustomProductListDto customProductListDto,
            HttpServletRequest request) {
        Long listId = productService.createProductList(customProductListDto.getTitle(), ProductListItem.ProductListType.CUSTOM,
                RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(listId == null ? ApiResponse.of(ApiStatus.INTERNAL_ERROR) :
                ApiResponseId.of(ApiStatus.LIST_CREATED, listId));
    }

    @PostMapping("/product/list/{listId}/remove")
    @CrossOrigin(origins = "*")
    @ApiOperation("Delete product list by id")
    public ResponseEntity<ApiResponse> removeProductList(@PathVariable long listId, HttpServletRequest request) {
        boolean isRemoved = productService.removeProductListById(listId, RequestUtils.getMiniAppToken(request));
        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.LIST_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }

    @GetMapping("/product/list/{listId}")
    @CrossOrigin(origins = "*")
    @ApiOperation("Get short form products by list id.")
    public ResponseEntity<Collection<ShortProductCard>> getProductCardsByListId(HttpServletRequest request,
            @PathVariable long listId) {
        return ResponseEntity.ok(productService.getProductCardsByListId(listId, RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/{listId}/add")
    @CrossOrigin(origins = "*")
    @ApiOperation("Add products to list")
    public ResponseEntity<ApiResponse> addProductToList(@RequestBody ProductIdDto productIdDto, @PathVariable long listId,
            HttpServletRequest request) {
        boolean isAdded = productService.addProductToList(productIdDto.getProductId(), listId, RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(isAdded ? ApiResponse.of(ApiStatus.PRODUCT_ADDED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }

    @PostMapping("/product/list/{listId}/product/{productId}/remove")
    @CrossOrigin(origins = "*")
    @ApiOperation("Remove products from list")
    public ResponseEntity<ApiResponse> removeProductFromList(@PathVariable long productId, HttpServletRequest request,
            @PathVariable long listId) {
        boolean isRemoved = productService.removeProductFromList(productId, listId, RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.PRODUCT_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }
}
