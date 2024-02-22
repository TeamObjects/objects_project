package com.objects.marketbridge.product.mock;

import com.objects.marketbridge.domains.product.domain.ProdTag;
import com.objects.marketbridge.domains.product.dto.ProdTagDto;
import com.objects.marketbridge.domains.product.service.port.ProdTagCustomRepository;
import com.objects.marketbridge.domains.product.service.port.ProdTagRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FakeProdTagRepository implements ProdTagRepository, ProdTagCustomRepository {
    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    List<ProdTag> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(ProdTag prodTag) {
        if(prodTag.getId() == null || prodTag.getId() == 0){
            data.add(ProdTag.builder()
                    .id(autoGeneratedId.incrementAndGet())
                    .tag(prodTag.getTag())
                    .product(prodTag.getProduct())
                    .build()
            );
        }else {
            data.removeIf(item -> Objects.equals(item.getId(), prodTag.getId()));
            data.add(prodTag);
        }
    }

    @Override
    public void saveAll(List<ProdTag> prodTags) {
        for (ProdTag prodTag: prodTags) {
            save(prodTag);
        }
    }

    @Override
    public List<ProdTagDto> findAllByProductId(Long productId) {
        List<ProdTagDto> result = new ArrayList<>();
        for (ProdTag prodTag:data) {
            if(prodTag.getProduct().getId() == productId){
                ProdTagDto prodTagDto = ProdTagDto.builder()
                        .tagKey(prodTag.getTag().getTagCategory().getName())
                        .tagValue(prodTag.getTag().getName())
                        .build();
                result.add(prodTagDto);
            }
        }
        return result;
    }
}
