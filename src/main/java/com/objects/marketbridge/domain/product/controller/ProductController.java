package com.objects.marketbridge.domain.product.controller;

import com.objects.marketbridge.domain.product.dto.ProductRequestDto;
import com.objects.marketbridge.domain.product.dto.ProductResponseDto;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import com.objects.marketbridge.domain.product.service.ProductService;
import com.objects.marketbridge.global.common.ApiResponse;
import com.objects.marketbridge.global.security.mock.UserAuthorize;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;


    //상품등록
    @UserAuthorize
    @PostMapping("/products")
    public ApiResponse<ProductResponseDto> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        Long productId = productService.registerProduct(productRequestDto);
        ProductResponseDto productResponseDto = new ProductResponseDto(productId);
        return ApiResponse.ok(productResponseDto);
    }



    //상품조회
    @GetMapping("/products")
    public ProductResponseDto getProductList(@Valid @RequestBody ProductRequestDto request){

        // 담을거 가져오기
        // 현재 임시로 기본생성자 사용.
        return new ProductResponseDto();
    }



//    //상품수정
//    @UserAuthorize
//    @PatchMapping("/products/{id}")
//    public ApiResponse<ProductResponseDto> UpdateProduct
//        (@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
//
//        ProductResponseDto productResponseDto = new ProductResponseDto(
//                productRequestDto.getCategoryId(),
//                productRequestDto.getIsOwn(),
//                productRequestDto.getName(),
//                productRequestDto.getPrice(),
//                productRequestDto.getIsSubs(),
//                productRequestDto.getStock(),
//                productRequestDto.getThumbImg(),
//                productRequestDto.getItemImgUrls(),
//                productRequestDto.getDetailImgUrls(),
//                productRequestDto.getDiscountRate(),
//                productRequestDto.getOptionNames());
//
//        productService.updateProduct(id, productRequestDto);
//        return ApiResponse.ok(productResponseDto);
//    }







    //상품삭제



}
