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
    @ApiOperation(value = "Возвращает все карточки продуктов")
    public ResponseEntity<Collection<ProductCard>> getProductCards(@RequestParam Long count, @RequestParam Long offset,
            HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductCards(count, offset, RequestUtils.getMiniAppToken(request)));
    }

    @GetMapping("/product/list")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Возваращет все списки пользователя")
    public ResponseEntity<Collection<ProductListItem>> getProductListItems(HttpServletRequest request) {
        return ResponseEntity.ok(productService.getProductListItems(RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/add")
    @CrossOrigin(origins = "*")
    @ApiOperation(value = "Добавляет пользователю новый список")
    public ResponseEntity<? extends ApiResponse> createProductList(@RequestBody CustomProductListDto customProductListDto,
            HttpServletRequest request) {
        Long listId = productService.createProductList(customProductListDto.getTitle(),
                RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(listId == null ? ApiResponse.of(ApiStatus.INTERNAL_ERROR) :
                ApiResponseId.of(ApiStatus.LIST_CREATED, listId));
    }

    @PostMapping("/product/list/{listId}/remove")
    @CrossOrigin(origins = "*")
    @ApiOperation("Удаляет список пользователя")
    public ResponseEntity<ApiResponse> removeProductList(@PathVariable long listId, HttpServletRequest request) {
        boolean isRemoved = productService.removeProductListById(listId, RequestUtils.getMiniAppToken(request));
        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.LIST_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }

    @GetMapping("/product/list/{listId}")
    @CrossOrigin(origins = "*")
    @ApiOperation("Возвращает рубашки товаров из выбранного списка")
    public ResponseEntity<Collection<ShortProductCard>> getProductCardsByListId(HttpServletRequest request,
            @PathVariable long listId) {
        return ResponseEntity.ok(productService.getProductCardsByListId(listId, RequestUtils.getMiniAppToken(request)));
    }

    @PostMapping("/product/list/{listId}/add")
    @CrossOrigin(origins = "*")
    @ApiOperation("Добавляет товар в список")
    public ResponseEntity<ApiResponse> addProductToList(@RequestBody ProductIdDto productIdDto, @PathVariable long listId,
            HttpServletRequest request) {
        boolean isAdded = productService.addProductToList(productIdDto.getProductId(), listId, RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(isAdded ? ApiResponse.of(ApiStatus.PRODUCT_ADDED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }

    @PostMapping("/product/list/{listId}/product/{productId}/remove")
    @CrossOrigin(origins = "*")
    @ApiOperation("Удаляет товар из списка")
    public ResponseEntity<ApiResponse> removeProductFromList(@PathVariable long productId, HttpServletRequest request,
            @PathVariable long listId) {
        boolean isRemoved = productService.removeProductFromList(productId, listId, RequestUtils.getMiniAppToken(request));

        return ResponseEntity.ok(isRemoved ? ApiResponse.of(ApiStatus.PRODUCT_REMOVED) :
                ApiResponse.of(ApiStatus.INTERNAL_ERROR));
    }
}
