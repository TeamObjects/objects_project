package com.objects.marketbridge.cart.service;

import com.objects.marketbridge.cart.domain.Cart;
import com.objects.marketbridge.cart.service.dto.GetCartDto;
import com.objects.marketbridge.cart.service.port.CartQueryRepository;
import com.objects.marketbridge.common.interceptor.SliceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GetCartListService {

    private final CartQueryRepository cartQueryRepository;

    public SliceResponse<GetCartDto> get(Pageable pageable, Long memberId) {

        Slice<Cart> slicedCart = cartQueryRepository.findSlicedCart(pageable, memberId);
        Slice<GetCartDto> cartInfos = new SliceImpl<>(convertEntityToDto(slicedCart.getContent()), pageable, slicedCart.hasNext());

        return new SliceResponse<>(cartInfos);
    }

    private List<GetCartDto> convertEntityToDto(List<Cart> carts) {
        return carts.stream().map(GetCartDto::of).toList();
    }
}