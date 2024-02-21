package com.objects.marketbridge.product.controller;

import com.objects.marketbridge.product.controller.request.CreateProductRequestDto;
import com.objects.marketbridge.product.dto.ProductSimpleDto;
import com.objects.marketbridge.product.service.*;
import com.objects.marketbridge.common.interceptor.ApiResponse;
import com.objects.marketbridge.common.security.annotation.UserAuthorize;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

////    private final BulkUploadProductService bulkUploadProductService;
    private final ProductService productService;
//    private final UpdateProductService updateProductService;
//    private final DeleteProductService deleteProductService;
//
//
//    //상품들 Excel파일로 대량등록
////    @UserAuthorize
////    @PostMapping("/uploadExcel")
////    public String uploadExcelFile(@RequestParam("file") MultipartFile file){
////        return bulkUploadProductService.uploadExcelFile(file);
////    }
//
//
    //상품등록
    @UserAuthorize
    @PostMapping("/new")
    public ApiResponse<Long> createProduct(@Valid @RequestBody CreateProductRequestDto request) {
        Long productId = productService.create(request);
        return ApiResponse.ok(productId);
    }
//
//
    //상품 카테고리별 조회
    @UserAuthorize
    @GetMapping()
    public ApiResponse<Page<ProductSimpleDto>> getProductByCategory(@PageableDefault(page = 1, size = 60, sort = "createdAt", direction = Sort.Direction.DESC)  Pageable pageable
            , @RequestParam("categoryCode") String categoryId){
        Page<ProductSimpleDto> productPage = productService.getProductByCategory(pageable,categoryId);
        return ApiResponse.ok(productPage);
    }


    @UserAuthorize
    @GetMapping("/{id}")
    public void getProductDetail (@PathVariable("id") Long id){
        productService.getProductDetail(id);
    }
//
//
//    //상품수정
//    @UserAuthorize
//    @PatchMapping("/{id}")
//    public ApiResponse<UpdateProductResponseDto> updateProduct
//    (@PathVariable("id") Long id, @RequestBody @Valid UpdateProductRequestDto updateProductRequestDto) {
//        UpdateProductResponseDto updateProductResponseDto
//                = updateProductService.update(updateProductRequestDto);
//        return ApiResponse.ok(updateProductResponseDto);
//    }
//
//
//    //상품삭제
//    @UserAuthorize
//    @DeleteMapping("/{id}")
//    public ApiResponse<String> deleteProduct
//    (@PathVariable("id") Long id) {
//        deleteProductService.delete(id);
//        return ApiResponse.ok("success");
//    }
}
