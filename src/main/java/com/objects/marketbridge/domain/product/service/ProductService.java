package com.objects.marketbridge.domain.product.service;

import com.objects.marketbridge.domain.product.dto.ProductRequestDto;
import com.objects.marketbridge.domain.Image.ImageRepository;
import com.objects.marketbridge.domain.Image.ProductImageRepository;
import com.objects.marketbridge.domain.product.repository.ProductRepository;
import com.objects.marketbridge.domain.category.CategoryRepository;
import com.objects.marketbridge.domain.option.OptionRepository;
import com.objects.marketbridge.domain.option.ProdOptionRepository;
import com.objects.marketbridge.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ProductImageRepository productImageRepository;
    private final OptionRepository optionRepository;
    private final ProdOptionRepository prodOptionRepository;

    // 상품등록
    @Transactional
    public Long registerProduct(ProductRequestDto productRequestDto) {

        // category가 DB에 등록되어있다고 가정.
        Category category = categoryRepository.findById(productRequestDto.getCategoryId());

        // ProductRequestDto에서 필요한 정보 추출하여 Product 엔터티 생성
        Product product = Product.builder()
                .category(category)
                .isOwn(productRequestDto.getIsOwn())
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .isSubs(productRequestDto.getIsSubs())
                .stock(productRequestDto.getStock())
                .thumbImg(productRequestDto.getThumbImg())
                .discountRate(productRequestDto.getDiscountRate())
                .build();

        // ProductRepositoryImpl 통해 엔터티를 저장
        productRepository.save(product);

                // 상품등록시 image테이블에 아이템이미지url들 추가, product_image테이블에 추가.
        List<String> itemImgUrls = productRequestDto.getItemImgUrls();
        for (String itemImgUrl : itemImgUrls) {
            Image itemImg = Image.builder()
                    .type(ImageType.ITEM_IMG.toString())
                    .url(itemImgUrl).build();
            imageRepository.save(itemImg);

            ProductImage productImage = ProductImage.builder()
                    .image(imageRepository.findById(itemImg.getId()))
                    .product(productRepository.findById(product.getId()))
                    .build();

            productImageRepository.save(productImage);

        }

        // 상품등록시 image테이블에 디테일이미지url들 추가, product_image테이블에 추가.
        List<String> detailImgUrls = productRequestDto.getDetailImgUrls();
        for (String detailImgUrl : detailImgUrls) {
            Image detailImg = Image.builder()
                    .type(ImageType.DETAIL_IMG.toString())
                    .url(detailImgUrl).build();
            imageRepository.save(detailImg);

            ProductImage productImage = ProductImage.builder()
                    .image(imageRepository.findById(detailImg.getId()))
                    .product(productRepository.findById(product.getId()))
                    .build();

            productImageRepository.save(productImage);
        }


        // product ----- product_option ---- options ---- option_category 연결되어 있음.
        // 1. option_category테이블에 데이터가 등록되어있다고 가정. (색상, 사이즈 등)
        // 2. options테이블에도 데이터가 등록되어 있다고 가정. (색상-white, 사이즈-XL 등. 따로 api만들어야할듯.)
        // 하나의 상품에대한 재고는(stock)은 options테이블의 해당옵션의 stock(재고)수임.
        // 3. 등록 => ProductRequestDto에 optionNames(String배열)를 Json형식으로 받아와서
        // product(id)와 option(id)가 등록되게 prod_option테이블에 등록.
        List<String> optionNames = productRequestDto.getOptionNames();
        for (String optionName : optionNames) {
            ProdOption prodOption = ProdOption.builder()
                    .product(productRepository.findById(product.getId()))
                    .option(optionRepository.findByName(optionName))
                    .build();

            prodOptionRepository.save(prodOption);
        }

        Long productId = product.getId();
        return productId;
    }



    // 상품조회
    public void getProductList(){
    }



//    // 상품수정
//    @Transactional
//    public void updateProduct (Long id, ProductRequestDto productRequestDto) {
//
//        // 상품 ID에 해당하는 상품을 찾음
//        Product findProduct = productRepository.findById(id);
//        Category category = categoryRepository.findById(productRequestDto.getCategoryId());
//
//        if(findProduct == null){
//            throw new EntityNotFoundException("해당 id에 해당하는 상품이 없습니다.");
//        }
//
//        // 업데이트 요청의 내용을 상품에 저장
//        findProduct.set
//
//        // 상품 업데이트 및 저장
//        productRepository.save(findProduct);
//    }




    // 상품삭제
}
