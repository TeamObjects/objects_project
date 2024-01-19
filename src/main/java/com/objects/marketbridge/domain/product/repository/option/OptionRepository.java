package com.objects.marketbridge.domain.product.repository.option;

import com.objects.marketbridge.domain.model.Option;

public interface OptionRepository {

    void save(Option option);

    Option findById(Long id);

    Option findByName(String name);
}
