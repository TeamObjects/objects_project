package com.objects.marketbridge.domain.option;

import com.objects.marketbridge.model.ProdOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProdOptionRepositoryImpl implements ProdOptionRepository {

    private final ProdOptionJpaRepository prodOptionJpaRepository;

    @Override
    public void save(ProdOption prodOption) {
        prodOptionJpaRepository.save(prodOption);
    }
}
